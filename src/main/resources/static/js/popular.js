const btnSelect = document.querySelector('.btn-select');

btnSelect.addEventListener('click', ()=>{
  btnSelect.classList.toggle('on');
})

// //2. li를 누르면('click') 최애프로그래밍 언어의 textContent가 this로 바뀜.
const buttons = document.querySelectorAll('li button')

buttons.forEach((item)=>{
   item.addEventListener('click', ()=>{
     btnSelect.textContent = item.textContent;
     btnSelect.classList.remove('on');
     //li를 선택하면 textContent가 바뀌고 on을 remove해줌으로써 list가 없어짐.
   })
})