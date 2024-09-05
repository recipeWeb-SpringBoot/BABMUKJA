// 로고 타이핑
document.addEventListener("DOMContentLoaded", function() {
    const text = "| R E C I P E S |";
    const textElement = document.getElementById("recipes-title"); // 여기부분 id수정
    let index = 0;
    const typingSpeed = 150; // 타이핑 속도 (밀리초)
    const pauseDuration = 30000; // 30초 간격 (밀리초)

    function typeText() {
        if (index < text.length) {
            textElement.innerHTML += text.charAt(index);
            index++;
            setTimeout(typeText, typingSpeed);
        } else {
            setTimeout(() => {
                textElement.innerHTML = ''; // 텍스트 초기화
                index = 0; // 인덱스 초기화
                typeText(); // 타이핑 다시 시작
            }, pauseDuration);
        }
    }

    typeText(); // 첫 타이핑 시작
});


//search
document.querySelector('.search-bar input').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        const query = e.target.value;
        window.location.href = `/mainSearch?query=${encodeURIComponent(query)}`;
    }
});

// category nav
// document.addEventListener('DOMContentLoaded', function() {
//     const navItems = document.querySelectorAll('.nav-item');
//
//     // 현재 URL에 따라 활성화된 카테고리를 설정하거나, 기본적으로 '한식' 선택
//     const currentPath = window.location.pathname;
//     let activeSet = false;
//
//     navItems.forEach(item => {
//         const hrefPath = new URL(item.href).pathname;
//         if (hrefPath === currentPath) {
//             item.classList.add('active');
//             activeSet = true;
//         }
//     });
//
//     // 만약 현재 URL과 일치하는 항목이 없다면, 기본적으로 '한식'을 활성화
//     if (!activeSet) {
//         const defaultItem = document.getElementById('default-category');
//         if (defaultItem) {
//             defaultItem.classList.add('active');
//         }
//     }
//
//     // 클릭 이벤트 설정 (클릭 시 active 클래스 업데이트)
//     navItems.forEach(item => {
//         item.addEventListener('click', function() {
//             navItems.forEach(nav => nav.classList.remove('active'));
//             this.classList.add('active');
//         });
//     });
// });

document.addEventListener('DOMContentLoaded', function() {
    const navItems = document.querySelectorAll('.nav-item');
    const currentPath = window.location.pathname;
    let activeSet = false;

    // 현재 경로가 "/" 또는 "/main"이라면 기본적으로 한식으로 리다이렉트
    if (currentPath === "/" || currentPath === "/main") {
        window.location.href = "/recipes/한식";
        return; // 한식 경로로 리다이렉트 후 아래 코드를 실행하지 않음
    }

    // 현재 URL에 따라 활성화된 카테고리를 설정
    navItems.forEach(item => {
        const hrefPath = new URL(item.href).pathname;
        if (hrefPath === currentPath) {
            item.classList.add('active');
            activeSet = true;
        }
    });

    // 클릭 이벤트 설정 (클릭 시 active 클래스 업데이트)
    navItems.forEach(item => {
        item.addEventListener('click', function() {
            navItems.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');
        });
    });
});
