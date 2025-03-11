import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './category.reducer';

export const CategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categoryEntity = useAppSelector(state => state.category.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="framasaasApp.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.id}</dd>
          <dt>
            <span id="categoryName">
              <Translate contentKey="framasaasApp.category.categoryName">Category Name</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.categoryName}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="framasaasApp.category.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.imagePath}</dd>
          <dt>
            <span id="vendorCategoryId">
              <Translate contentKey="framasaasApp.category.vendorCategoryId">Vendor Category Id</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.vendorCategoryId}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.category.description">Description</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.description}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.category.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.category.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {categoryEntity.createdTime ? <TextFormat value={categoryEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.category.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.category.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {categoryEntity.updatedTime ? <TextFormat value={categoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.category.franchiseRule">Franchise Rule</Translate>
          </dt>
          <dd>{categoryEntity.franchiseRule ? categoryEntity.franchiseRule.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;
