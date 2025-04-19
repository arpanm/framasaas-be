import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order.reducer';

export const ServiceOrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderEntity = useAppSelector(state => state.serviceOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrder.detail.title">ServiceOrder</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.serviceOrder.description">Description</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.description}</dd>
          <dt>
            <span id="orderType">
              <Translate contentKey="framasaasApp.serviceOrder.orderType">Order Type</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.orderType}</dd>
          <dt>
            <span id="orderStatus">
              <Translate contentKey="framasaasApp.serviceOrder.orderStatus">Order Status</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.orderStatus}</dd>
          <dt>
            <span id="inWarranty">
              <Translate contentKey="framasaasApp.serviceOrder.inWarranty">In Warranty</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.inWarranty ? 'true' : 'false'}</dd>
          <dt>
            <span id="isFree">
              <Translate contentKey="framasaasApp.serviceOrder.isFree">Is Free</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.isFree ? 'true' : 'false'}</dd>
          <dt>
            <span id="isSpareNeeded">
              <Translate contentKey="framasaasApp.serviceOrder.isSpareNeeded">Is Spare Needed</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.isSpareNeeded ? 'true' : 'false'}</dd>
          <dt>
            <span id="confirmedTime">
              <Translate contentKey="framasaasApp.serviceOrder.confirmedTime">Confirmed Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderEntity.confirmedTime ? (
              <TextFormat value={serviceOrderEntity.confirmedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="closedTime">
              <Translate contentKey="framasaasApp.serviceOrder.closedTime">Closed Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderEntity.closedTime ? (
              <TextFormat value={serviceOrderEntity.closedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="serviceCharge">
              <Translate contentKey="framasaasApp.serviceOrder.serviceCharge">Service Charge</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.serviceCharge}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="framasaasApp.serviceOrder.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.tax}</dd>
          <dt>
            <span id="totalSpareCharges">
              <Translate contentKey="framasaasApp.serviceOrder.totalSpareCharges">Total Spare Charges</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.totalSpareCharges}</dd>
          <dt>
            <span id="totalSpareTax">
              <Translate contentKey="framasaasApp.serviceOrder.totalSpareTax">Total Spare Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.totalSpareTax}</dd>
          <dt>
            <span id="totalCharges">
              <Translate contentKey="framasaasApp.serviceOrder.totalCharges">Total Charges</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.totalCharges}</dd>
          <dt>
            <span id="totalPaymentDone">
              <Translate contentKey="framasaasApp.serviceOrder.totalPaymentDone">Total Payment Done</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.totalPaymentDone}</dd>
          <dt>
            <span id="customerInvoicePath">
              <Translate contentKey="framasaasApp.serviceOrder.customerInvoicePath">Customer Invoice Path</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.customerInvoicePath}</dd>
          <dt>
            <span id="nps">
              <Translate contentKey="framasaasApp.serviceOrder.nps">Nps</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.nps}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="framasaasApp.serviceOrder.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.priority}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrder.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrder.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrder.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderEntity.createdTime ? (
              <TextFormat value={serviceOrderEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrder.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrder.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderEntity.updatedTime ? (
              <TextFormat value={serviceOrderEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrder.customer">Customer</Translate>
          </dt>
          <dd>{serviceOrderEntity.customer ? serviceOrderEntity.customer.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrder.serviceMaster">Service Master</Translate>
          </dt>
          <dd>{serviceOrderEntity.serviceMaster ? serviceOrderEntity.serviceMaster.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrder.article">Article</Translate>
          </dt>
          <dd>{serviceOrderEntity.article ? serviceOrderEntity.article.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrder.address">Address</Translate>
          </dt>
          <dd>{serviceOrderEntity.address ? serviceOrderEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order/${serviceOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderDetail;
