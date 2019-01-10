package com.askdog.service.impl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "askdog.score.answer.weight")
public class AnswerWeightConfiguration {

    private int share;
    private int voteUp;
    private int voteDown;
    private int favorite;

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
