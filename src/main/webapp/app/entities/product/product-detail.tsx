import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product.reducer';

export const ProductDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productEntity = useAppSelector(state => state.product.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="framasaasApp.product.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="productName">
              <Translate contentKey="framasaasApp.product.productName">Product Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.productName}</dd>
          <dt>
            <span id="vendorProductId">
              <Translate contentKey="framasaasApp.product.vendorProductId">Vendor Product Id</Translate>
            </span>
          </dt>
          <dd>{productEntity.vendorProductId}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.product.description">Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="framasaasApp.product.price">Price</Translate>
            </span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="productType">
              <Translate contentKey="framasaasApp.product.productType">Product Type</Translate>
            </span>
          </dt>
          <dd>{productEntity.productType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.product.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{productEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.product.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.createdTime ? <TextFormat value={productEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.product.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{productEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.product.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.updatedTime ? <TextFormat value={productEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.product.category">Category</Translate>
          </dt>
          <dd>{productEntity.category ? productEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.product.brand">Brand</Translate>
          </dt>
          <dd>{productEntity.brand ? productEntity.brand.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.product.hsn">Hsn</Translate>
          </dt>
          <dd>{productEntity.hsn ? productEntity.hsn.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductDetail;
