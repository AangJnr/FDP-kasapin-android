package org.grameen.fdp.kasapin.data.db.entity;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by AangJnr on 26, September, 2018 @ 12:29 PM
 * Work Mail cibrahim@grameenfoundation.org
 * Personal mail aang.jnr@gmail.com
 */

public class FormAndQuestions {
    @Embedded Form form;

    @Relation(parentColumn = "id", entityColumn = "formTranslationId", entity = Question.class)
    public List<Question> questions;

    public void setForm(Form form) {
        this.form = form;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Form getForm() {
        return form;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
