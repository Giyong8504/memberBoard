// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => { //클릭 시
        let id = document.getElementById('board-id').value;
        fetch(`/api/board/${id}`, { //api 요청 보내는 역할
            method: 'DELETE'
        })
        .then(() => {
            alert('삭제 되었습니다.');
            location.replace('/board');
        });
    });
}


// 수정 기능
// id가 modify-btn인 엘리먼트를 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트 발생시 수정 API 요청
    modifyButton.addEventListener('click', event => {
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
        .then(() => {
            alert('수정 되었습니다.');
            location.replace(`/board/${id}`);
        });
    });
}


// 등록 기능
// id가 create-btn인 엘리먼트를 조회.
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        // 등록 API로 POST 요청을 보낸다.
        fetch(`/api/board`, {
            method: 'POST',
            headers: { // 요청을 보낼 때 headers의 요청 형식을 지정한다.
                "Content-Type": "application/json",
            },

            // body에 HTML에서 입력한 데이터를 JSON 형식으로 바꿔 보낸다.
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                author: document.getElementById('author').value
            })
        })
        .then(() => {
            alert('등록 되었습니다.');
            location.replace('/board');
        });
    });
}