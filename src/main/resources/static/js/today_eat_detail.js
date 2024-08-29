//북마크 변경 기능 -> 이걸 지울까?말까?
document.querySelectorAll('.bookmark-icon').forEach(icon => {
    icon.addEventListener('click', function() {
        if (this.src.includes('bookmark.png')) {
            this.src = 'images/bookmark_filled.png';
        } else {
            this.src = 'images/bookmark.png';
        }
    });
});


//별점 나열 기능
document.querySelectorAll('.sort-option').forEach(option => {
    option.addEventListener('click', function() {
        document.querySelectorAll('.sort-option').forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');

        // 여기에 별점 순서에 따라 레시피를 정렬하는 코드를 추가할 수 있습니다.
        // sortRecipes(this.id); // 예: sort-high 또는 sort-low에 따라 정렬
    });
});
var bookmarkSrc = '/images/bookmark.png';
var bookmarkFilledSrc = '/images/bookmark_filled.png';

//상세페이지 이동함수
document.addEventListener('DOMContentLoaded', function (){
    const recipeItems = document.querySelectorAll('.recipe-item');
    console.log("눌렸다")
    recipeItems.forEach(item => {
        item.addEventListener('click', function(){
            const hiddenInput = this.querySelector('input[type="hidden"]');
            const recipeId = hiddenInput.value;

            window.location.href = '/recipe/detail/' + recipeId;
        })
    })


    // //북마크가 설정되어있는지 확인하는 함수
    // var bookmarkIcon = document.getElementById('bookmark-icon');
    // var isBookmark = parseInt(document.querySelector('#isBook').value);  // 1 = true, 0 = false
    // console.log(isBookmark)
    // // 페이지 로드 시 북마크 상태를 반영
    // if (isBookmark === 1) {
    //     bookmarkIcon.src = bookmarkFilledSrc;
    // } else {
    //     bookmarkIcon.src = bookmarkSrc;
    // }


})