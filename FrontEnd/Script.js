let number1_input=document.getElementById("number-1");
let number2_input=document.getElementById("number-2");
let button_sum=document.getElementById("sum");
let button_dif=document.getElementById("dif");
let button_com=document.getElementById("com");
let button_div=document.getElementById("div");
let Conclusion=document.getElementById("Conclusion");

button_sum.onclick=function(event){
    executeRequest("sum");
};
button_dif.onclick=function(event){
    executeRequest("dif")
};
button_com.onclick=function(event){
    executeRequest("com")
};
button_div.onclick=function(event){
    executeRequest("div")
};

function executeRequest(operationAddress) {
    let numOne = number1_input.value;
    let numTwo = number2_input.value;
    fetch(`http://localhost:8080/${operationAddress}?firstNum=${numOne}&secondNum=${numTwo}`)
        .then((response) => {
            return response.text()
          })
          .then((data) => {
            Conclusion.innerHTML="Вывод: " + data;
          });
}