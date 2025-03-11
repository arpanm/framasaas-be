import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hsn.reducer';

export const HsnDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hsnEntity = useAppSelector(state => state.hsn.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hsnDetailsHeading">
          <Translate contentKey="framasaasApp.hsn.detail.title">Hsn</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.id}</dd>
          <dt>
            <span id="hsnCD">
              <Translate contentKey="framasaasApp.hsn.hsnCD">Hsn CD</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.hsnCD}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.hsn.description">Description</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.description}</dd>
          <dt>
            <span id="taxRate">
              <Translate contentKey="framasaasApp.hsn.taxRate">Tax Rate</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.taxRate}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.hsn.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.hsn.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.createdTime ? <TextFormat value={hsnEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.hsn.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.hsn.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{hsnEntity.updatedTime ? <TextFormat value={hsnEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/hsn" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hsn/${hsnEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HsnDetail;
