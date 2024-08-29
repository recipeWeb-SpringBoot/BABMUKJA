document.querySelectorAll('.bookmark-icon').forEach(icon => {
    icon.addEventListener('click', function() {
        if (this.src.includes('bookmark.png')) {
            this.src = 'images/bookmark_filled.png';
        } else {
            this.src = 'images/bookmark.png';
        }
    });
});


document.querySelectorAll('.sort-option').forEach(option => {
    option.addEventListener('click', function() {
        document.querySelectorAll('.sort-option').forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');

        // 여기에 별점 순서에 따라 레시피를 정렬하는 코드를 추가할 수 있습니다.
        // sortRecipes(this.id); // 예: sort-high 또는 sort-low에 따라 정렬
    });
});


function searchRecipes() {
    const query = document.getElementById('search').value.toLowerCase();
    const recipes = document.querySelectorAll('.recipe-item');
    
    recipes.forEach(recipe => {
        const title = recipe.querySelector('h3').innerText.toLowerCase();
        if (title.includes(query)) {
            recipe.style.display = 'block';
        } else {
            recipe.style.display = 'none';
        }
    });
}
