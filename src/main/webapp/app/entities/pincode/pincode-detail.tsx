import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pincode.reducer';

export const PincodeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pincodeEntity = useAppSelector(state => state.pincode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pincodeDetailsHeading">
          <Translate contentKey="framasaasApp.pincode.detail.title">Pincode</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pincodeEntity.id}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="framasaasApp.pincode.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{pincodeEntity.pincode}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.pincode.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{pincodeEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.pincode.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {pincodeEntity.createdTime ? <TextFormat value={pincodeEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.pincode.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{pincodeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.pincode.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {pincodeEntity.updatedTime ? <TextFormat value={pincodeEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.pincode.franchiseRule">Franchise Rule</Translate>
          </dt>
          <dd>{pincodeEntity.franchiseRule ? pincodeEntity.franchiseRule.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.pincode.fieldAgentSkillRule">Field Agent Skill Rule</Translate>
          </dt>
          <dd>{pincodeEntity.fieldAgentSkillRule ? pincodeEntity.fieldAgentSkillRule.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pincode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pincode/${pincodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PincodeDetail;
