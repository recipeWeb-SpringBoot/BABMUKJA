var modal = document.getElementById("Modal");
var slider = document.getElementById("difficultySlider");
var Button = document.getElementById("submitBtn");
var sliderText = document.getElementById("sliderValue");
var open = document.getElementById("openModalBtn");

// 모달 열기
open.onclick = () => {
    modal.style.display = "block";
}

// 모달 닫기
document.querySelector(".close").onclick = () => {
    modal.style.display = "none";
}

// 사용자 입력값 확인
slider.oninput = () => {
    sliderText.innerHTML = "현재값 : " + slider.value;
}

// 데이터 전송 (평가 제출 후 즉시 반영)
Button.onclick = (event) => {
    event.preventDefault();

    var sliderValue = slider.value;
    console.log(sliderValue); // 입력값 확인

    // 전송 로직
    fetch("/addDifficulty", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ difficulty: sliderValue })
    })
        .then(response => response.json())
        .then(data => {
            console.log("Success: ", data);
            modal.style.display = "none";
            updateDifficultyRatings(); // 평가 결과 즉시 반영
        })
        .catch(error => {
            console.error("Error: ", error);
        });
};


// 슬라이더 위치를 동적으로 업데이트하는 함수
function updateSlider(easy = null, hard = null) {
    const sliderBar = document.querySelector('.slider-bar');

    // 퍼센티지가 모두 1% 이상일때 색상 변경
    if (easy !== null && hard !== null && (easy >= 1 || hard >= 1)) {
        sliderBar.style.background = `linear-gradient(to right, #9AD19A ${easy}%, #FFB1B1 ${easy}%)`;
    } else {
        sliderBar.style.background = '#D9D9D9'; // 기본 회색
    }
}

// 평가 결과 업데이트
function updateDifficultyRatings() {
    const recipeId = document.getElementById('recipeId').value;

    // 서버에서 평가 결과 가져오기
    fetch(`/getDifficulty?recipeId=${recipeId}`)
        .then(response => response.json())
        .then(data => {
            const easyPercentage = data.easy !== undefined && data.easy !== null ? data.easy : null;
            const hardPercentage = data.hard !== undefined && data.hard !== null ? data.hard : null;

            // 퍼센티지가 있을 때만 슬라이더 바를 업데이트, 평가가 없으면 0%로 유지
            document.querySelector(".easy-percentage").innerText = easyPercentage !== null ? `${easyPercentage}%` : '0%';
            document.querySelector(".hard-percentage").innerText = hardPercentage !== null ? `${hardPercentage}%` : '0%';

            // 평가가 1% 이상일 때만 슬라이더 바의 색상 업데이트
            updateSlider(easyPercentage, hardPercentage);
        })
        .catch(error => {
            console.error("Error fetching difficulty ratings: ", error);
            // 오류 발생 시 기본 회색 슬라이더로 유지
            updateSlider(); // 기본 회색 유지
        });
}

// 페이지가 로드될 때 평가 결과 불러오기
window.onload = () => {
    updateDifficultyRatings();  // 평가 결과 가져오기
};

