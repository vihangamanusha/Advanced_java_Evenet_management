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