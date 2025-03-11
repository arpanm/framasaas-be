import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './language-mapping.reducer';

export const LanguageMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const languageMappingEntity = useAppSelector(state => state.languageMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="languageMappingDetailsHeading">
          <Translate contentKey="framasaasApp.languageMapping.detail.title">LanguageMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{languageMappingEntity.id}</dd>
          <dt>
            <span id="lang">
              <Translate contentKey="framasaasApp.languageMapping.lang">Lang</Translate>
            </span>
          </dt>
          <dd>{languageMappingEntity.lang}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.languageMapping.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{languageMappingEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.languageMapping.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {languageMappingEntity.createdTime ? (
              <TextFormat value={languageMappingEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.languageMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{languageMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.languageMapping.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {languageMappingEntity.updatedTime ? (
              <TextFormat value={languageMappingEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.languageMapping.franchiseRule">Franchise Rule</Translate>
          </dt>
          <dd>{languageMappingEntity.franchiseRule ? languageMappingEntity.franchiseRule.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.languageMapping.fieldAgentSkillRule">Field Agent Skill Rule</Translate>
          </dt>
          <dd>{languageMappingEntity.fieldAgentSkillRule ? languageMappingEntity.fieldAgentSkillRule.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/language-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/language-mapping/${languageMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LanguageMappingDetail;
