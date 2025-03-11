import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-price-history.reducer';

export const ProductPriceHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productPriceHistoryEntity = useAppSelector(state => state.productPriceHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productPriceHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.productPriceHistory.detail.title">ProductPriceHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productPriceHistoryEntity.id}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="framasaasApp.productPriceHistory.price">Price</Translate>
            </span>
          </dt>
          <dd>{productPriceHistoryEntity.price}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.productPriceHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{productPriceHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.productPriceHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {productPriceHistoryEntity.updatedTime ? (
              <TextFormat value={productPriceHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.productPriceHistory.product">Product</Translate>
          </dt>
          <dd>{productPriceHistoryEntity.product ? productPriceHistoryEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/product-price-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-price-history/${productPriceHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductPriceHistoryDetail;
