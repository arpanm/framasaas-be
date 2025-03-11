import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-user.reducer';

export const FranchiseUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseUserEntity = useAppSelector(state => state.franchiseUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseUserDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseUser.detail.title">FranchiseUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.id}</dd>
          <dt>
            <span id="userName">
              <Translate contentKey="framasaasApp.franchiseUser.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.userName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="framasaasApp.franchiseUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.email}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="framasaasApp.franchiseUser.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.contact}</dd>
          <dt>
            <span id="userStatus">
              <Translate contentKey="framasaasApp.franchiseUser.userStatus">User Status</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.userStatus}</dd>
          <dt>
            <span id="userRole">
              <Translate contentKey="framasaasApp.franchiseUser.userRole">User Role</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.userRole}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseUser.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseUser.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseUserEntity.createdTime ? (
              <TextFormat value={franchiseUserEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseUser.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseUserEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseUser.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseUserEntity.updatedTime ? (
              <TextFormat value={franchiseUserEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseUser.franchise">Franchise</Translate>
          </dt>
          <dd>{franchiseUserEntity.franchise ? franchiseUserEntity.franchise.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseUser.skillRuleSet">Skill Rule Set</Translate>
          </dt>
          <dd>{franchiseUserEntity.skillRuleSet ? franchiseUserEntity.skillRuleSet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-user/${franchiseUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseUserDetail;
