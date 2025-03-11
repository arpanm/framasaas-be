import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-payment.reducer';

export const ServiceOrderPaymentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderPaymentEntity = useAppSelector(state => state.serviceOrderPayment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderPaymentDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderPayment.detail.title">ServiceOrderPayment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.id}</dd>
          <dt>
            <span id="paymentLink">
              <Translate contentKey="framasaasApp.serviceOrderPayment.paymentLink">Payment Link</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.paymentLink}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="framasaasApp.serviceOrderPayment.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.paymentStatus}</dd>
          <dt>
            <span id="mop">
              <Translate contentKey="framasaasApp.serviceOrderPayment.mop">Mop</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.mop}</dd>
          <dt>
            <span id="pgTxnId">
              <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnId">Pg Txn Id</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.pgTxnId}</dd>
          <dt>
            <span id="pgTxnResponse">
              <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnResponse">Pg Txn Response</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.pgTxnResponse}</dd>
          <dt>
            <span id="pgTxnResponseTime">
              <Translate contentKey="framasaasApp.serviceOrderPayment.pgTxnResponseTime">Pg Txn Response Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderPaymentEntity.pgTxnResponseTime ? (
              <TextFormat value={serviceOrderPaymentEntity.pgTxnResponseTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="paymentInitiatedBy">
              <Translate contentKey="framasaasApp.serviceOrderPayment.paymentInitiatedBy">Payment Initiated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.paymentInitiatedBy}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrderPayment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderPayment.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderPayment.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderPaymentEntity.createdTime ? (
              <TextFormat value={serviceOrderPaymentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderPayment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderPaymentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderPayment.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderPaymentEntity.updatedTime ? (
              <TextFormat value={serviceOrderPaymentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/service-order-payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order-payment/${serviceOrderPaymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderPaymentDetail;
