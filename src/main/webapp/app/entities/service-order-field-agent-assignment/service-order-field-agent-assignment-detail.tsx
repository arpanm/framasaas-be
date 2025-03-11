import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-field-agent-assignment.reducer';

export const ServiceOrderFieldAgentAssignmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderFieldAgentAssignmentEntity = useAppSelector(state => state.serviceOrderFieldAgentAssignment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderFieldAgentAssignmentDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.detail.title">ServiceOrderFieldAgentAssignment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.id}</dd>
          <dt>
            <span id="serviceOrderAssignmentStatus">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.serviceOrderAssignmentStatus">
                Service Order Assignment Status
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.serviceOrderAssignmentStatus}</dd>
          <dt>
            <span id="nps">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.nps">Nps</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.nps}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="assignedTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.assignedTime">Assigned Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.assignedTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.assignedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="movedBackTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.movedBackTime">Moved Back Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.movedBackTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.movedBackTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="visitTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.visitTime">Visit Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.visitTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.visitTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareOrderTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.spareOrderTime">Spare Order Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.spareOrderTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.spareOrderTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareDeliveryTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.spareDeliveryTime">Spare Delivery Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.spareDeliveryTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.spareDeliveryTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completedTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.completedTime">Completed Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.completedTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.completedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completionOTP">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.completionOTP">Completion OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.completionOTP}</dd>
          <dt>
            <span id="cancellationOTP">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.cancellationOTP">Cancellation OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.cancellationOTP}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.createdTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFieldAgentAssignmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFieldAgentAssignmentEntity.updatedTime ? (
              <TextFormat value={serviceOrderFieldAgentAssignmentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/service-order-field-agent-assignment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/service-order-field-agent-assignment/${serviceOrderFieldAgentAssignmentEntity.id}/edit`}
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

export default ServiceOrderFieldAgentAssignmentDetail;
