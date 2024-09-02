
window.onload = function() {
    const url = new URL(window.location.href);
    if (url.searchParams.has('query')) {
        url.searchParams.delete('query');
        window.history.replaceState({}, document.title, url.pathname);
    }
}

// document.addEventListener('DOMContentLoaded', function () {
//     const navItems = document.querySelectorAll('.nav-item');
//
//     // 기본값 설정 (식재료)
//     const defaultNavItem = document.querySelector('.nav-item[data-target="ingredient"]');
//     defaultNavItem.classList.add('default');
//
//     navItems.forEach(item => {
//         if (!item.classList.contains('default')) {
//             item.classList.add('inactive');
//         }
//
//         item.addEventListener('click', () => {
//             // 모든 항목의 활성 클래스 제거
//             navItems.forEach(nav => nav.classList.remove('default', 'inactive'));
//
//             // 클릭된 항목에 활성 클래스 추가
//             item.classList.add('default');
//             navItems.forEach(nav => {
//                 if (!nav.classList.contains('default')) {
//                     nav.classList.add('inactive');
//                 }
//             });
//         });
//     });
//
//     // 커스텀 드롭다운 메뉴 JavaScript
//     const btnSelect = document.querySelector('.btn-select');
//     const list = document.querySelector('.list');
//     const buttons = document.querySelectorAll('.list li button');
//
//     btnSelect.addEventListener('click', (event) => {
//         event.stopPropagation();
//         btnSelect.classList.toggle('on');
//         list.style.display = list.style.display === 'block' ? 'none' : 'block';
//     });
//
//     buttons.forEach(button => {
//         button.addEventListener('click', (event) => {
//             event.stopPropagation();
//             btnSelect.textContent = button.textContent;
//             btnSelect.classList.remove('on');
//             list.style.display = 'none';
//         });
//     });
//
//     // 드롭다운 외부를 클릭했을 때 닫기
//     document.addEventListener('click', () => {
//         btnSelect.classList.remove('on');
//         list.style.display = 'none';
//     });
// });
