/*
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

package com.samtech.hibernate3.impl;

import java.util.List;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class AbstractEntityService {

    private javax.persistence.EntityManagerFactory entityManagerFactory;
    private JpaTemplate jpaTemplate;

    private TransactionTemplate transactionTemplate;

    public AbstractEntityService() {
        super();
    }

    public final void setEntityManagerFactory(javax.persistence.EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.jpaTemplate = new JpaTemplate(this.entityManagerFactory);
    }

    @SuppressWarnings("unchecked")
    protected <T > T merge(final T entity) {
        return (T) transactionTemplate.execute(new TransactionCallback(){
            public Object doInTransaction(TransactionStatus status) {
                T merged = jpaTemplate.merge(entity);
                jpaTemplate.flush();
                return merged;
            }});
    }

    protected <T > T load(Class<T> entityClass, Integer entityId) {
        return jpaTemplate.find(entityClass, entityId);
    }

    @SuppressWarnings("unchecked")
    protected <T > List<T> findAll(Class<T> entityClass) {
        return jpaTemplate.find("select distinct e from " + entityClass.getName() + " e");
    }

    public JpaTemplate getJpaTemplate() {
        return jpaTemplate;
    }

    public void setJpaTemplate(JpaTemplate jpaTemplate) {
        this.jpaTemplate = jpaTemplate;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
