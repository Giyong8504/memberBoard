// 회원 게시글 삭제 기능
const adminDeleteButton = document.querySelectorAll('.adminDelete-btn');

if (adminDeleteButton) {
    adminDeleteButton.forEach(button => {
        button.addEventListener('click', event => {
            // 사용자에게 확인을 받기 위한 경고창 표시
            const isConfirmed = confirm('삭제 하시겠습니까?');
            if (isConfirmed) {
                let params = new URLSearchParams(location.search);
                let id = button.getAttribute('data-board-id');

                fetch(`/api/userBoardList/${id}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        // 서버에서 삭제가 성공적으로 이루어진 경우
                        alert('삭제 되었습니다.');
                        location.replace('/admin/userBoardList');
                    } else {
                        // 서버에서 삭제가 실패한 경우 또는 권한이 없는 경우
                        alert('삭제 권한이 없습니다.');
                    }
                })
            }
        });
    });
}


// 회원 강제 탈퇴 기능
const userDeleteByAdminButton = document.querySelectorAll('.userDeleteByAdmin-btn');

if (userDeleteByAdminButton) {
    userDeleteByAdminButton.forEach(button => {
        button.addEventListener('click', event => {
            // 사용자에게 확인을 받기 위한 경고창 표시
            const isConfirmed = confirm('삭제 하시겠습니까?');
            if (isConfirmed) {
                let params = new URLSearchParams(location.search);
                let id = button.getAttribute('data-user-id');

                fetch(`/api/userList/${id}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        // 서버에서 삭제가 성공적으로 이루어진 경우
                        alert('삭제 되었습니다.');
                        location.replace('/admin/userList');
                    } else {
                        // 서버에서 삭제가 실패한 경우 또는 권한이 없는 경우
                        alert('삭제 권한이 없습니다.');
                    }
                })
            }
        });
    });
}