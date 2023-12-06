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