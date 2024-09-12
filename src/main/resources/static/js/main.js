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
let currentPage = 0;
let currentCategory = '한식'; // '한식' default

function loadCategory(category) {
    currentCategory = category;
    currentPage = 0; // 페이지 초기화
    fetch(`/Category?category=${category}&page=${currentPage}`)
        .then(response => response.text())
        .then(html => {
            // 레시피 목록만 업데이트
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const recipeList = doc.querySelector('#recipe-list').innerHTML;
            document.getElementById("recipe-list").innerHTML = recipeList;
            document.getElementById("load-more").style.display = 'block';
        });
}

function loadMore() {
    currentPage++;
    fetch(`/Category/more?category=${currentCategory}&page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            let container = document.getElementById("recipe-list");
            data.forEach(recipe => {
                let newRecipe = document.createElement("div");
                newRecipe.innerHTML = `
                        <div class="recipe">
                            <a href="/recipe/detail/${recipe.id}" style="color: #142618; text-decoration: none;">
                                ${recipe.mediaUrl.endsWith('.jpg') || recipe.mediaUrl.endsWith('.jpeg') || recipe.mediaUrl.endsWith('.png') ?
                    `<img src="${recipe.mediaUrl}" alt="${recipe.title}" />` :
                    `<video controls src="${recipe.mediaUrl}" alt="${recipe.title}">Your browser does not support the video tag.</video>`}
                                <h3>${recipe.title}</h3>
                                <div class="ingredient-tags">
                                    ${Object.keys(recipe.ingredients).map(ingredient => `<p class="ingredient-tag">${ingredient}</p>`).join('')}
                                </div>
                            </a>
                        </div>
                    `;
                container.appendChild(newRecipe);
            });

            if (data.length === 0) {
                document.getElementById("load-more").style.display = 'none';
            }
        });
}

window.onload = function() {
    loadCategory('한식');
};

// 클릭 이벤트
document.addEventListener('DOMContentLoaded', function() {
    const navItems = document.querySelectorAll('.nav-item');

    // 기본 '한식' 카테고리
    const defaultCategory = document.getElementById('default-category');
    defaultCategory.classList.add('active');

    // 클릭 이벤트 설정 (클릭 시 active 클래스 업데이트)
    navItems.forEach(item => {
        item.addEventListener('click', function() {
            // 모든 nav-item에서 active 클래스 제거
            navItems.forEach(nav => nav.classList.remove('active'));
            // 현재 클릭된 카테고리에 active 클래스 추가
            this.classList.add('active');
        });
    });
});