// 이 부분을 JavaScript 코드의 최상단 또는 필요한 범위(예: 함수)에서 선언합니다.
var bookmarkSrc = '/images/bookmark.png';
var bookmarkFilledSrc = '/images/bookmark_filled.png';

document.addEventListener("DOMContentLoaded", function () {
    var bookmarkIcon = document.getElementById('bookmark-icon');
    var isBookmark = parseInt(document.querySelector('#isBook').value);  // 1 = true, 0 = false

    // 페이지 로드 시 북마크 상태를 반영
    if (isBookmark === 1) {
        bookmarkIcon.src = bookmarkFilledSrc;
    } else {
        bookmarkIcon.src = bookmarkSrc;
    }

    const recipeItems = document.querySelectorAll('.footer-recipe-item');
    console.log("눌렸다")
    recipeItems.forEach(item => {
        item.addEventListener('click', function(){
            const hiddenInput = this.querySelector('input[type="hidden"]');
            const recipeId = hiddenInput.value;

            window.location.href = '/recipe/detail/' + recipeId;
        })
    })

});

function toggleBookmark() {
    var bookmarkIcon = document.getElementById('bookmark-icon');
    var currentSrc = bookmarkIcon.src;

    // 데이터베이스 쪽 북마크 유무 확인
    var bookmark = document.querySelector('#isBook');
    var isBookmark = parseInt(bookmark.value);  // 1 = true, 0 = false

    // 페이지에 할당되어 있는 레시피 아이디
    var recipe = document.querySelector('#recipeId');
    var recipeId = recipe.value;

    var checkBookmark;

    // 북마크 상태 변경
    if (currentSrc.includes('bookmark.png') || isBookmark === 0) {
        bookmarkIcon.src = bookmarkFilledSrc;
        checkBookmark = true;
        isBookmark = 1;
    } else if (currentSrc.includes('bookmark_filled.png') || isBookmark === 1) {
        bookmarkIcon.src = bookmarkSrc;
        checkBookmark = false;
        isBookmark = 0;
    }

    console.log('레시피아이디 : ', recipeId, '북마크할당 : ', checkBookmark, '북마크확인 : ', isBookmark);

    // 북마크 상태에 따른 전송 주소 설정
    const url = checkBookmark ? '/addBookmark' : '/removeBookmark';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            recipeId: recipeId,
            isBookmark: isBookmark
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                console.log(data);
                console.log('북마크 상태변경 성공.');
                bookmark.value = isBookmark; // 서버와 동기화된 상태로 값을 업데이트
            } else {
                console.log(data);
                console.error('북마크 상태변경 실패');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}


function toggleDetails(id) {
    var content = document.getElementById(id);
    var icon = document.getElementById(id + '-icon');
    var button = icon.closest('.toggle-button');

    // 컨텐츠 토글
    content.classList.toggle('active');

    // 아이콘 회전
    icon.classList.toggle('active');

    // 접근성을 위한 aria-expanded 속성 토글
    var isExpanded = content.classList.contains('active');
    button.setAttribute('aria-expanded', isExpanded);
}




// function toggleBookmark() {
//     var bookmarkIcon = document.getElementById('bookmark-icon');
//     var currentSrc = bookmarkIcon.src;
//
//     var bookmarkSrc = currentSrc.replace('bookmark_filled.png', 'bookmark.png');
//     var bookmarkFilledSrc = currentSrc.replace('bookmark.png', 'bookmark_filled.png');
//
//     if (currentSrc.includes('bookmark.png')) {
//         bookmarkIcon.src = bookmarkFilledSrc;
//     } else {
//         bookmarkIcon.src = bookmarkSrc;
//     }
// }

