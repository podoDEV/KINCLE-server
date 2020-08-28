package com.podo.climb.model.Inner;

import com.podo.climb.model.MembersBoardFlagType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersBoardFlag {
    private Boolean likeFlag = null;
    private Boolean solveFlag = null;
    private Boolean followFlag = null;

    public MembersBoardFlag(MembersBoardFlagType membersBoardFlagType) {
        switch (membersBoardFlagType) {
            case LIKE:
                this.likeFlag = true;
                break;
            case DISLIKE:
                this.likeFlag = false;
                break;
            case SOLVED:
                this.solveFlag = true;
                break;
            case UNSOLVED:
                this.solveFlag = false;
                break;
            case FOLLOW:
                this.followFlag = true;
            case UNFOLLOW:
                this.followFlag = false;
        }
    }
}
