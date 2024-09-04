document.addEventListener('DOMContentLoaded', () => {
    const fridgeInput = document.getElementById('fridge-items');
    const excludeInput = document.getElementById('exclude-items');
    const fridgeTagsContainer = document.querySelector('.fridge-tags');
    const excludeTagsContainer = document.querySelector('.exclude-tags');
    const hiddenFridgeTags = document.getElementById('hidden-fridge-tags');
    const hiddenExcludeTags = document.getElementById('hidden-exclude-tags');
    const searchButton = document.querySelector('.search-button');

    const fridgeTags = new Set();
    const excludeTags = new Set();

    function updateHiddenFields() {
        hiddenFridgeTags.value = Array.from(fridgeTags).join(',');
        hiddenExcludeTags.value = Array.from(excludeTags).join(',');
    }

    function createTagElement(tag, container, tagsSet, hiddenFieldUpdater) {
        if (tag.trim() === '') {
            alert('빈 태그는 추가할 수 없습니다.');
            return false;
        }

        if (tagsSet.has(tag)) {
            alert('이미 추가된 태그입니다.');
            return false;
        }

        const tagElement = document.createElement('div');
        tagElement.classList.add('tag');
        tagElement.textContent = tag;

        const closeElement = document.createElement('span');
        closeElement.classList.add('close');
        closeElement.textContent = '×';

        closeElement.addEventListener('click', () => {
            container.removeChild(tagElement);
            tagsSet.delete(tag);
            hiddenFieldUpdater();
        });

        tagElement.appendChild(closeElement);
        container.appendChild(tagElement);

        tagsSet.add(tag);
        hiddenFieldUpdater();
        return true;
    }

    function handleTagInput(inputElement, container, tagsSet) {
        if (inputElement.value.trim() !== '') {
            const tag = inputElement.value.trim();
            if (createTagElement(tag, container, tagsSet, updateHiddenFields)) {
                inputElement.value = '';
            }
        } else {
            alert('태그를 입력해주세요.');
        }
    }

    fridgeInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            handleTagInput(fridgeInput, fridgeTagsContainer, fridgeTags);
        }
    });

    excludeInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            handleTagInput(excludeInput, excludeTagsContainer, excludeTags);
        }
    });

    searchButton.addEventListener('click', (event) => {
        updateHiddenFields();
        if (fridgeTags.size === 0 && excludeTags.size === 0) {
            alert('최소한 하나의 태그를 추가해주세요.');
            event.preventDefault();
        }
    });
});