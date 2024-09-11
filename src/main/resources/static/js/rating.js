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
            alert('전송');
            modal.style.display = "none";
        })
        .catch(error => {
            console.error("Error: ", error);
        });
}