// 댓글 데이터를 담을 배열
let comments = [];

// 댓글 로딩 함수 (페이지 로드 시 실행)
$(document).ready(function() {
    loadComments();
});

// 댓글 로딩 함수
function loadComments() {
    // Ajax를 이용하여 서버에서 댓글 데이터 가져오기
    $.ajax({
        type: "GET",
        url: "getComments.php", // 실제로는 서버에서 댓글을 가져오는 API 엔드포인트를 지정해야 합니다.
        success: function(data) {
            comments = data;
            displayComments();
        }
    });
}

// 댓글 표시 함수
function displayComments() {
    let commentsContainer = $("#comments");
    commentsContainer.empty();

    // 댓글을 화면에 표시
    comments.forEach(function(comment) {
        let commentElement = $("<div>").text(comment.text);

        // 댓글의 댓글 표시
        if (comment.replies && comment.replies.length > 0) {
            let repliesContainer = $("<div>").addClass("replies");
            comment.replies.forEach(function(reply) {
                let replyElement = $("<div>").text(reply.text);
                repliesContainer.append(replyElement);
            });
            commentElement.append(repliesContainer);
        }

        commentsContainer.append(commentElement);
    });
}

// 댓글 등록 함수
function addComment() {
    let commentInput = $("#commentInput");
    let newCommentText = commentInput.val();

    // Ajax를 이용하여 서버에 새 댓글 데이터 전송
    $.ajax({
        type: "POST",
        url: "addComment.php", // 실제로는 서버에 새 댓글을 추가하는 API 엔드포인트를 지정해야 합니다.
        data: { text: newCommentText },
        success: function() {
            // 댓글 추가 후, 다시 댓글 로딩 및 표시
            loadComments();
            commentInput.val(""); // 입력창 비우기
        }
    });
}