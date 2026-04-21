function openLogin() {
    document.getElementById("loginModal").style.display = "block";
}

function closeLogin() {
    document.getElementById("loginModal").style.display = "none";
}

function openSignup() {
    document.getElementById("signupModal").style.display = "block";
}

function closeSignup() {
    document.getElementById("signupModal").style.display = "none";
}

function showSignup() {
    closeLogin();
    openSignup();
}

function showLogin() {
    closeSignup();
    openLogin();
}

function showMember() {
    document.getElementById("memberForm").style.display = "block";
    document.getElementById("publicForm").style.display = "none";
}

function showPublic() {
    document.getElementById("memberForm").style.display = "none";
    document.getElementById("publicForm").style.display = "block";
}

function validateMember() {
    let pwd = document.getElementById("memberPwd").value;
    let confirmPwd = document.getElementById("memberConfirmPwd").value;
    let error = document.getElementById("memberError");

    if (pwd !== confirmPwd) {
        error.innerText = "❌ Passwords do not match!";
        return false; // ❗ this is what prevents account creation
    }

    error.innerText = "";
    return true;
}

function validatePublic() {
    let pwd = document.getElementById("publicPwd").value;
    let confirmPwd = document.getElementById("publicConfirmPwd").value;
    let error = document.getElementById("publicError");

    if (pwd !== confirmPwd) {
        error.innerText = "❌ Passwords do not match!";
        return false; // ❗ this is what prevents account creation
    }

    error.innerText = "";
    return true;
}
