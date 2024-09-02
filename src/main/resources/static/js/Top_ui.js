// document.addEventListener('DOMContentLoaded', () => {
//     const navItems = document.querySelectorAll('nav ul li');

//     navItems.forEach(item => {
//         item.addEventListener('click', () => {
//             // 모든 항목에서 selected 클래스 제거
//             navItems.forEach(i => i.classList.remove('selected'));
//             // 클릭된 항목에 selected 클래스 추가
//             item.classList.add('selected');
//         });
//     });
// });

document.addEventListener('DOMContentLoaded', () => {
    const navItems = document.querySelectorAll('nav ul li');

    navItems.forEach(item => {
        item.addEventListener('click', () => {
            // 모든 항목에서 selected 클래스 제거
            navItems.forEach(i => i.classList.remove('selected'));
            // 클릭된 항목에 selected 클래스 추가
            item.classList.add('selected');
        });
    });
});

document.addEventListener("DOMContentLoaded", function() {
    var logoImg = document.querySelector(".logo img");
    var dropdown = document.getElementById("dropdown-content");

    function updateDropdownPosition() {
        var rect = logoImg.getBoundingClientRect();
        dropdown.style.top = (rect.bottom + window.scrollY + 20) + "px";
        dropdown.style.left = (rect.left + window.scrollX - 230) + "px";
    }

    logoImg.addEventListener("click", function(event) {
        updateDropdownPosition();
        dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
    });

    window.addEventListener("resize", function() {
        if (dropdown.style.display === "block") {
            updateDropdownPosition();
        }
    });

    window.addEventListener("click", function(event) {
        if (!event.target.closest('.logo img') && !event.target.closest('#dropdown-content')) {
            dropdown.style.display = "none";
        }
    });

    // 호버 이벤트 추가
    const buttons = document.querySelectorAll('#dropdown-content a.edit-info, #dropdown-content .combined-buttons a');
    buttons.forEach(button => {
        button.addEventListener('mouseover', function() {
            this.style.backgroundColor = '#E6F6E7';
            this.style.color = '#385F41';
            const img = this.querySelector('img');
            if (img) {
                img.style.filter = 'invert(0%) sepia(0%) saturate(0%) hue-rotate(0deg) brightness(0%) contrast(100%)';
            }
        });

        button.addEventListener('mouseout', function() {
            this.style.backgroundColor = '#385F41';
            this.style.color = '#fff';
            const img = this.querySelector('img');
            if (img) {
                img.style.filter = 'none';
            }
        });
    });
});