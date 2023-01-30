package org.cmbookrental.prj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 만화책 DTO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
public class ComicBookDTO {
    //만화책 ID (중복 허용 안함)
    private int bookID;
    //만화책 명
    private String title;
    //작가명
    private String author;

    @Override
    public int hashCode() {
        return bookID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ComicBookDTO){
            ComicBookDTO target = (ComicBookDTO) obj;
            return target.title.equals(title) && target.author.equals(author);
        } else return false;
    }

    @Override
    public String toString() {
        return "만화책 ID : " + bookID + ", 만화책명 : " + title + ", 작가 : " + author;
    }
}
