import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article-warranty-details.reducer';

export const ArticleWarrantyDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleWarrantyDetailsEntity = useAppSelector(state => state.articleWarrantyDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleWarrantyDetailsDetailsHeading">
          <Translate contentKey="framasaasApp.articleWarrantyDetails.detail.title">ArticleWarrantyDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.id}</dd>
          <dt>
            <span id="warrantyType">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.warrantyType">Warranty Type</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.warrantyType}</dd>
          <dt>
            <span id="vendorArticleWarrantyId">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.vendorArticleWarrantyId">Vendor Article Warranty Id</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.vendorArticleWarrantyId}</dd>
          <dt>
            <span id="vendorWarrantyMasterId">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.vendorWarrantyMasterId">Vendor Warranty Master Id</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.vendorWarrantyMasterId}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsEntity.startDate ? (
              <TextFormat value={articleWarrantyDetailsEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsEntity.endDate ? (
              <TextFormat value={articleWarrantyDetailsEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsEntity.createdTime ? (
              <TextFormat value={articleWarrantyDetailsEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{articleWarrantyDetailsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.articleWarrantyDetails.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {articleWarrantyDetailsEntity.updatedTime ? (
              <TextFormat value={articleWarrantyDetailsEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.articleWarrantyDetails.article">Article</Translate>
          </dt>
          <dd>{articleWarrantyDetailsEntity.article ? articleWarrantyDetailsEntity.article.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/article-warranty-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article-warranty-details/${articleWarrantyDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleWarrantyDetailsDetail;
