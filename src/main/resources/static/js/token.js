const token = searchParam('token') // 토큰이 있다면 로컬 스토리지에 토큰 저장

if (token) {
    localStorage.setItem("access_token", token)
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}