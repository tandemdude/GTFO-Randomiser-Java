function showform() {
  document.getElementById("overlay-form").style.display = "block";
}

function hideform() {
  document.getElementById("overlay-form").style.display = "none";
}

$(document).ready(function () {
  $('#submissionForm').submit(function(e) {
    e.preventDefault();
    $.ajax({
      url: '/api/v1/daily/submission',
      type: 'post',
      data: $('#submissionForm').serialize(),
      success: function () {
        alert("Run submitted successfully");
        location.reload();
      }
    });
  });
});
