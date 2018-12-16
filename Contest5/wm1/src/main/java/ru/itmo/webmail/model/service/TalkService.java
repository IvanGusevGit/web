package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.AttributePair;
import ru.itmo.webmail.model.repository.Repository;
import ru.itmo.webmail.model.repository.impl.RepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalkService {

    private Repository<Talk> talkRepository = new RepositoryImpl<>(Talk.class);
    private Repository<User> userService = new RepositoryImpl<>(User.class);

    public void saveTalk(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Message> makeMessageList(User user) {
        List<Message> messages = new ArrayList<>();
        List<Talk> talks = talkRepository.findAll();
        for (Talk talk : talks) {
            if (talk.getSourceUserId() == user.getId() || talk.getTargetUserId() == user.getId()) {
                User sourceUser, targetUser;
                if (talk.getSourceUserId() == user.getId()) {
                    sourceUser = user;
                    targetUser = userService.findByAttributes(new AttributePair<>("id", talk.getTargetUserId()));
                } else {
                    sourceUser = userService.findByAttributes(new AttributePair<>("id", talk.getSourceUserId()));
                    targetUser = user;
                }
                Message message = new Message();
                message.setId(talk.getId());
                message.setFrom(sourceUser.getLogin());
                message.setTo(targetUser.getLogin());
                message.setContent(talk.getText());
                message.setTime(talk.getCreationTime());
                messages.add(message);
            }
        }
        return messages;
    }

    public class Message {
        private long id;
        private String from;
        private String to;
        private String content;
        private Date time;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }
}
