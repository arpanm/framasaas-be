import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article-warranty-details-document.reducer';

export const ArticleWarrantyDetailsDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleWarrantyDetailsDocumentEntity = useAppSelector(state => state.articleWarrantyDetailsDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleWarrantyDetailsDocumentDetailsHeading">
          <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.detail.title">ArticleWarrantyDetailsDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.id}</dd>
          <dt>
            <span id="documentPath">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.documentPath">Document Path</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.documentPath}</dd>
          <dt>
            <span id="isValid">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.isValid">Is Valid</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.isValid ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsDocumentEntity.createdTime ? (
              <TextFormat value={articleWarrantyDetailsDocumentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsDocumentEntity.updatedTime ? (
              <TextFormat value={articleWarrantyDetailsDocumentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.articleWarranty">Article Warranty</Translate>
          </dt>
          <dd>{articleWarrantyDetailsDocumentEntity.articleWarranty ? articleWarrantyDetailsDocumentEntity.articleWarranty.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/article-warranty-details-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/article-warranty-details-document/${articleWarrantyDetailsDocumentEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleWarrantyDetailsDocumentDetail;
