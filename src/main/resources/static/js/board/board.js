// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
    // 사용자에게 확인을 받기 위한 경고창 표시
    const isConfirmed = confirm('삭제 하시겠습니까?');
        if (isConfirmed) {
            let id = document.getElementById('board-id').value;

            fetch(`/api/board/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    // 서버에서 삭제가 성공적으로 이루어진 경우
                    alert('삭제 되었습니다.');
                    location.replace('/board');
                } else {
                    // 서버에서 삭제가 실패한 경우 또는 권한이 없는 경우
                    alert('삭제 권한이 없습니다.');
                }
            })
        }
    });
}


// 수정 기능
// id가 modify-btn인 엘리먼트를 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트 발생시 수정 API 요청
    modifyButton.addEventListener('click', event => {
    const isConfirmed = confirm('수정 하시겠습니까?');
        if (isConfirmed) {
            let params = new URLSearchParams(location.search);
            let id = params.get('id');

            // 요청을 보낼 때 headers의 요청 형식을 지정
            fetch(`/api/board/${id}`, {
                method: 'PUT', // 수정 요청 메소드
                headers: {
                    "Content-Type": "application/json",
                },

                // body에 HTML에 입력한 데이터를 JSON 형식으로 바꿔 보낸다.
                body: JSON.stringify({
                    title: document.getElementById('title').value,
                    content: document.getElementById('content').value
                })
            })
            .then(response => {
                if (response.ok) {
                    alert('수정되었습니다.');
                    location.replace(`/board/${id}`);
                } else {
                    // 403 Forbidden: 수정 권한이 없는 경우
                    alert('수정 권한이 없습니다.');
                }
            })
        }
    });
}


// 등록 기능
// id가 create-btn인 엘리먼트를 조회.
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {

    const isConfirmed = confirm('등록하시겠습니까?')
        if (isConfirmed) {
            // body에 HTML에서 입력한 데이터를 JSON 형식으로 바꿔 보낸다.
            body = JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                author: document.getElementById('author').value
            });
            function success() {
                alert('등록 되었습니다.');
                location.replace('/board');
            };

            function fail() {
                alert('등록 실패했습니다.');
                location.replace('/board');
            };

            httpRequest('POST','/api/board', body, success, fail)
        };
    });
}


// 댓글 등록 기능
// id가 commentAdd-btn인 엘리먼트를 조회.
const commentAddButton = document.getElementById('commentAdd-btn');

if (commentAddButton) {
    commentAddButton.addEventListener('click', event => {

    const isConfirmed = confirm('댓글을 등록하시겠습니까?')
        if(isConfirmed) {
            // 등록 API로 POST 요청을 보낸다.
            fetch(`/api/comments`, {
                method: 'POST',
                headers: { // 요청을 보낼 때 headers의 요청 형식을 지정한다.
                    "Content-Type": "application/json",
                },

                // body에 HTML에서 입력한 데이터를 JSON 형식으로 바꿔 보낸다.
                body: JSON.stringify({
                    content: document.getElementById('commentAdd-content').value,
                    author: document.getElementById('commentAdd-author').value,
                    boardDataId: document.getElementById('commentAdd-boardId').value
                })
            })
            .then(response => {
                if (response.ok) {
                    alert('댓글이 등록되었습니다.');
                    location.replace(`/board/{id}`);
                } else {
                    alert('댓글 권한이 없습니다.');
                }
            });
        }
    });
}


// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';'); // ; 를 기준으로 나눠 배열로 저장
    cookie.some(function (item) {
        item = item.replace(' ', ''); // 각 쿠키 문자열에서 공백 제거

        var dic = item.split('='); // 문자열을 '=' 기준으로 나눠 dic 배열에 저장

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, { // 지정된 URL에 HTTP 요청
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) { // 토큰 갱신
            fetch('/api/token', { // 새로운 엑세스 토큰 요청
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}


// 클라이언트에서 로그아웃 버튼 클릭 시 호출되는 함수
const logoutButton = document.getElementById('logout-btn');

if (logoutButton) {
    logoutButton.addEventListener('click', event => {
        fetch('/api/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('access_token')
            }
        })
        .then(response => {
            if (response.ok) {
                // 서버에서 로그아웃 성공 시
                localStorage.removeItem('access_token'); // 로컬 스토리지에서 토큰 제거
                alert('로그아웃 되었습니다.');
            } else {
                alert('로그아웃에 실패 했습니다.');
            }
        });
    });
}





