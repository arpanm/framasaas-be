import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article.reducer';

export const ArticleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleEntity = useAppSelector(state => state.article.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleDetailsHeading">
          <Translate contentKey="framasaasApp.article.detail.title">Article</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{articleEntity.id}</dd>
          <dt>
            <span id="serialNo">
              <Translate contentKey="framasaasApp.article.serialNo">Serial No</Translate>
            </span>
          </dt>
          <dd>{articleEntity.serialNo}</dd>
          <dt>
            <span id="vendorArticleId">
              <Translate contentKey="framasaasApp.article.vendorArticleId">Vendor Article Id</Translate>
            </span>
          </dt>
          <dd>{articleEntity.vendorArticleId}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.article.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{articleEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.article.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {articleEntity.createdTime ? <TextFormat value={articleEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.article.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{articleEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.article.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {articleEntity.updatedTime ? <TextFormat value={articleEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.article.product">Product</Translate>
          </dt>
          <dd>{articleEntity.product ? articleEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.article.customer">Customer</Translate>
          </dt>
          <dd>{articleEntity.customer ? articleEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/article" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article/${articleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleDetail;
