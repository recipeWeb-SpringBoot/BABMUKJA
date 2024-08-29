document.addEventListener('DOMContentLoaded', () => {
    const fridgeInput = document.getElementById('fridge-items');
    const excludeInput = document.getElementById('exclude-items');
    const fridgeTagsContainer = document.querySelector('.fridge-tags');
    const excludeTagsContainer = document.querySelector('.exclude-tags');
    const hiddenFridgeTags = document.getElementById('hidden-fridge-tags');
    const hiddenExcludeTags = document.getElementById('hidden-exclude-tags');
    const searchButton = document.querySelector('.search-button');

    const fridgeTags = new Set(); // 먹을 수 있는 재료 태그 저장
    const excludeTags = new Set(); // 못 먹는 재료 태그 저장

    // 숨겨진 필드를 업데이트하는 함수
    function updateHiddenFields() {
        hiddenFridgeTags.value = Array.from(fridgeTags).join(',');
        hiddenExcludeTags.value = Array.from(excludeTags).join(',');
    }

    // 태그 요소를 생성하는 함수
    function createTagElement(tag, container, tagsSet, hiddenFieldUpdater) {
        const tagElement = document.createElement('div');
        tagElement.classList.add('tag');
        tagElement.textContent = tag;

        const closeElement = document.createElement('span');
        closeElement.classList.add('close');
        closeElement.textContent = '×';

        // 태그 삭제 이벤트
        closeElement.addEventListener('click', () => {
            container.removeChild(tagElement);
            tagsSet.delete(tag);
            hiddenFieldUpdater();  // 태그 삭제 시 숨겨진 필드 업데이트
        });

        tagElement.appendChild(closeElement);
        container.appendChild(tagElement);

        hiddenFieldUpdater(); // 태그 추가 시 숨겨진 필드 업데이트
    }

    // 먹을 수 있는 재료 입력 처리
    fridgeInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter' && fridgeInput.value.trim() !== '') {
            const tag = fridgeInput.value.trim();
            if (!fridgeTags.has(tag)) {
                fridgeTags.add(tag);
                createTagElement(tag, fridgeTagsContainer, fridgeTags, updateHiddenFields);
            }
            fridgeInput.value = '';
            event.preventDefault();  // 폼이 제출되지 않도록 방지
        }
    });

    // 못 먹는 재료 입력 처리
    excludeInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter' && excludeInput.value.trim() !== '') {
            const tag = excludeInput.value.trim();
            if (!excludeTags.has(tag)) {
                excludeTags.add(tag);
                createTagElement(tag, excludeTagsContainer, excludeTags, updateHiddenFields);
            }
            excludeInput.value = '';
            event.preventDefault();  // 폼이 제출되지 않도록 방지
        }
    });

    // 검색 버튼 클릭 시 숨겨진 필드 업데이트
    searchButton.addEventListener('click', (event) => {
        updateHiddenFields();  // 폼 제출 전에 숨겨진 필드를 업데이트
    });
});