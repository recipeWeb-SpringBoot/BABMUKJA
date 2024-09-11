var modal = document.getElementById("Modal");
var slider = document.getElementById("difficultySlider");
var Button = document.getElementById("submitBtn");
var sliderText = document.getElementById("sliderValue");
var open = document.getElementById("openModalBtn");

//모달열기
open.onclick = () =>{
    modal.style.display = "block";
}

//모달 닫기
document.querySelector(".close").onclick = () => {
    modal.style.display = "none";
}

//사용자 입력값 확인
slider.oninput = () =>{
    sliderText.innerHTML = "현재값 : " + slider.value;
}

//데이터 전송
Button.onclick = (event) => {
    event.preventDefault();

    var sliderValue = slider.value;
    console.log(sliderValue) //일단 여기까지 넘어옴

    //전송 로직
    fetch("/addDifficulty", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({difficulty: sliderValue})
    })
        .then(response => response.json())
        .then(data => {
            console.log("Success: ", data);
            modal.style.display = "none";
        })
        .catch(error => {
            console.error("Error: ", error);
        });
}

window.onload = () =>{
    const easyElement = document.querySelector("#easyPercentage");
    const hardElement = document.querySelector("#hardPercentage");

    const easy = parseFloat(easyElement.value);
    const hard = parseFloat(hardElement.value);

    updateSlider(easy, hard);
}

function updateSlider(easy, hard) {
    const sliderCircle = document.querySelector('#slider-circle');
    let position = 50; // 초기 위치

    if (hard > easy) {
        position = 50 + (hard - easy);
    } else if (easy > hard) {
        position = 50 - (easy - hard);
    }

    // 슬라이더 위치 업데이트
    sliderCircle.style.left = `${position}%`;
}