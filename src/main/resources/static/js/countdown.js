// Update the count down every 1 second
var x = setInterval(function() {
  // let today = new Date().getTime();
  // let tomorrow = new Date(today);
  // tomorrow.setDate(tomorrow.getDate() + 1);
  // tomorrow.setHours(0, 0, 0, 0);

  let today = new Date();
  let now = today.setUTCHours(today.getUTCHours());
  let tomorrow = new Date(today);
  tomorrow.setDate(tomorrow.getDate() + 1);
  let timer_end = tomorrow.setUTCHours(0, 0, 0, 0);

  // Get today's date and time
  // var now = new Date().getTime();

  // Find the distance between now and the count down date
  var distance = timer_end - now;

  // Time calculations for days, hours, minutes and seconds
  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  var seconds = Math.floor((distance % (1000 * 60)) / 1000);

  // Display the result in the element with id="demo"
  document.getElementById("countdown").innerHTML = hours + "h "
  + minutes + "m " + seconds + "s ";

  // If the count down is finished, write some text
  if (distance < 0) {
    clearInterval(x);
    window.location.reload();
  }
}, 1000);