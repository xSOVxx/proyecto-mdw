document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const inputs = form.querySelectorAll(".form-control");
  
    inputs.forEach((input) => {
      input.addEventListener("input", () => {
        if (input.checkValidity()) {
          input.classList.add("is-valid");
          input.classList.remove("is-invalid");
        } else {
          input.classList.add("is-invalid");
          input.classList.remove("is-valid");
        }
      });
    });
  
    form.addEventListener("submit", function (event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add("was-validated");
    });
  });
  