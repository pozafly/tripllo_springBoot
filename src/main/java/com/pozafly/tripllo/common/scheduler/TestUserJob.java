package com.pozafly.tripllo.common.scheduler;

import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.card.dao.CardDao;
import com.pozafly.tripllo.checklist.dao.ChecklistDao;
import com.pozafly.tripllo.checklist.dao.ChecklistItemDao;
import com.pozafly.tripllo.comment.dao.CommentDao;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.list.dao.ListDao;
import com.pozafly.tripllo.user.model.request.UserApiRequest;
import com.pozafly.tripllo.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@Transactional
public class TestUserJob {

    @Autowired
    private UserService userService;
    @Autowired
    private BoardDao boardDao;
    @Autowired
    private ListDao listDao;
    @Autowired
    private CardDao cardDao;
    @Autowired
    private ChecklistDao checklistDao;
    @Autowired
    private ChecklistItemDao checklistItemDao;
    @Autowired
    private CommentDao commentDao;

    public void excute() {
        deleteUser();
        makeUser();
        makeBoard();
        makeList();
        makeCard();
        makeCheckList();
        makeCheckListItem();
        makeComment();
    }

    void deleteUser() {
        log.info("delete User 실행");

        ResponseEntity<Message> result = userService.rtnIdValid("test");
        // 유저가 존재한다면 지우고 시작한다.
        if (result.getStatusCode().toString().equals("401")) {
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", "test");
            userInfo.put("password", "test");

            userService.deleteUser(userInfo);
            log.info("user 삭제 완");
        }

        log.info("delete User 종료");
    }

    void makeUser() {
        log.info("Make User 실행");

        UserApiRequest userApiRequest = new UserApiRequest();
        userApiRequest.setId("test");
        userApiRequest.setPassword("test");
        userApiRequest.setEmail("test@test.com");
        userApiRequest.setName("테스트이름");

        userService.createUser(userApiRequest);
        log.info("Make User 종료");
    }

    Long boardId1;
    void makeBoard() {
        log.info("Make Board 실행");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", "test");
        map.put("title", "test id를 위한 sample 1 (샘플 Data O)");
        map.put("bgColor", "#339af0");
        map.put("publicYn", "Y");
        map.put("hashtag", "[\"고양이\"]");

        boardDao.createBoard(map);
        boardId1 = (Long) map.get("id");

        map.put("userId", "test");
        map.put("title", "sample 2");
        map.put("bgColor", "#3a4142");
        map.put("publicYn", "Y");
        map.put("hashtag", "[\"강아지\"]");

        boardDao.createBoard(map);

        map.put("userId", "test");
        map.put("title", "마음껏 test 가능.");
        map.put("bgColor", "#fa5252");
        map.put("publicYn", "Y");
        map.put("hashtag", "[\"고양이\"]");

        boardDao.createBoard(map);

        log.info("Make Board 종료");
    }

    Long listId1;
    Long listId2;
    private void makeList() {
        log.info("Make List 시작");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", "test");
        map.put("boardId", boardId1);
        map.put("title", "이곳을 눌러 title변경.");
        map.put("pos", 65535);

        listDao.createList(map);
        listId1 = (Long) map.get("id");

        map.put("userId", "test");
        map.put("boardId", boardId1);
        map.put("title", "카드작성 고");
        map.put("pos", 131070);

        listDao.createList(map);
        listId2 = (Long) map.get("id");

        map.put("userId", "list도 서로간 이동이 가능합니다.");
        map.put("boardId", boardId1);
        map.put("title", "test");
        map.put("pos", 262140);

        listDao.createList(map);

        log.info("Make List 종료");
    }

    Long cardId;
    void makeCard() {
        log.info("Make Card 종료");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", "test");
        map.put("listId", listId1);
        map.put("title", "카드를 눌러보세요.");
        map.put("pos", 65535);

        cardDao.createCard(map);

        map.put("userId", "test");
        map.put("listId", listId1);
        map.put("title", "아이콘이 붙어있는 카드는,");
        map.put("pos", 131070);

        cardDao.createCard(map);

        map.put("userId", "test");
        map.put("listId", listId1);
        map.put("title", "카드내부 기능을 이용, 수정시");
        map.put("pos", 262140);

        cardDao.createCard(map);

        map.put("userId", "test");
        map.put("listId", listId1);
        map.put("title", "자동으로 붙습니다.");
        map.put("pos", 524280);

        cardDao.createCard(map);

        map.put("userId", "test");
        map.put("listId", listId1);
        map.put("title", "카드를 드래그 해서 이동시켜보세요.");
        map.put("pos", 1048560);

        cardDao.createCard(map);

        map.put("userId", "test");
        map.put("listId", listId2);
        map.put("title", "미리 작성된 card");
        map.put("pos", 65535);

        cardDao.createCard(map);
        cardId = (Long) map.get("id");


        map.put("userId", "test");
        map.put("cardId", cardId);
        map.put("listId", listId2);
        map.put("title", "미리 작성된 card");
        map.put("pos", 65535);
        map.put("description", "디스크립션으로 메모가 가능합니다.");
        map.put("labelColor", "#fa5252,#4AC06A,#7950f2");
        map.put("location", "{\"lat\":37.526922,\"lng\":127.108381,\"address\":\"대한민국 서울특별시 송파구 풍납2동 올림픽로43길 88\",\"name\":\"서울아산병원\"}");
        map.put("dueDate", "202102121500");
        map.put("isChecklist", "Y");

        cardDao.updateCard(map);

        log.info("Make Card 종료");
    }

    Long checklistId;
    void makeCheckList() {
        log.info("Make checklist 시작");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", "test");
        map.put("cardId", cardId);
        map.put("title", "체크리스트는 여러개 만들 수 있습니다. 오른쪽 CheckList 클릭.");

        checklistDao.createChecklist(map);
        checklistId = (Long) map.get("id");

        log.info("Make checklist 종료");
    }

    Long checklistItemId;
    void makeCheckListItem() {
        log.info("Make checklistItem 시작");

        Map<String, Object> map = new HashMap<>();
        map.put("userId", "test");
        map.put("checklistId", checklistId);
        map.put("item", "여기를 눌러, 체크리스트를");
        checklistItemDao.createChecklistItem(map);

        map.put("userId", "test");
        map.put("checklistId", checklistId);
        map.put("item", "완료상태로 바꿔보세요.");
        checklistItemDao.createChecklistItem(map);

        map.put("userId", "test");
        map.put("checklistId", checklistId);
        map.put("item", "이렇게.");
        checklistItemDao.createChecklistItem(map);
        checklistItemId = (Long) map.get("id");


        map.put("userId", "test");
        map.put("checklistItemId", checklistItemId);
        map.put("isChecked", "Y");
        checklistItemDao.updateChecklistItem(map);

        log.info("Make checklistItem 종료");
    }

    void makeComment() {
        log.info("Make comment 시작");

        Map<String, Object> map = new HashMap<>();
        map.put("cardId", cardId);
        map.put("userId", "test");
        map.put("comment", "댓글을 작성할 수 있어요");
        map.put("dept", 0);
        map.put("groupNum", 1);
        commentDao.createComment(map);

        map.put("cardId", cardId);
        map.put("userId", "test");
        map.put("comment", "답글 달기도 가능.");
        map.put("dept", 1);
        map.put("groupNum", 1);
        commentDao.createComment(map);

        map.put("cardId", cardId);
        map.put("userId", "test");
        map.put("comment", "단, 답글이 있는 댓글을 삭제하면 '삭제되었습니다' 라고만 표시 됩니다.");
        map.put("dept", 1);
        map.put("groupNum", 1);
        commentDao.createComment(map);

        map.put("cardId", cardId);
        map.put("userId", "test");
        map.put("comment", "나를 지워보세요. 답글이 없기 때문에 그냥 지워집니다.");
        map.put("dept", 0);
        map.put("groupNum", 4);
        commentDao.createComment(map);

        log.info("Make comment 종료");
    }

}
