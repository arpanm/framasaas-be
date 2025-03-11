import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './field-agent-skill-rule.reducer';

export const FieldAgentSkillRule = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const fieldAgentSkillRuleList = useAppSelector(state => state.fieldAgentSkillRule.entities);
  const loading = useAppSelector(state => state.fieldAgentSkillRule.loading);
  const links = useAppSelector(state => state.fieldAgentSkillRule.links);
  const updateSuccess = useAppSelector(state => state.fieldAgentSkillRule.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="field-agent-skill-rule-heading" data-cy="FieldAgentSkillRuleHeading">
        <Translate contentKey="framasaasApp.fieldAgentSkillRule.home.title">Field Agent Skill Rules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.fieldAgentSkillRule.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/field-agent-skill-rule/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.fieldAgentSkillRule.home.createLabel">Create new Field Agent Skill Rule</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fieldAgentSkillRuleList ? fieldAgentSkillRuleList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fieldAgentSkillRuleList && fieldAgentSkillRuleList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('skillType')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.skillType">Skill Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('skillType')} />
                  </th>
                  <th className="hand" onClick={sort('joinType')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.joinType">Join Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('joinType')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.fieldAgentSkillRule.fieldAgentSkillRuleSet">Field Agent Skill Rule Set</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldAgentSkillRuleList.map((fieldAgentSkillRule, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/field-agent-skill-rule/${fieldAgentSkillRule.id}`} color="link" size="sm">
                        {fieldAgentSkillRule.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.FieldAgentSkillRuleType.${fieldAgentSkillRule.skillType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.FieldAgentSkillRuleJoinType.${fieldAgentSkillRule.joinType}`} />
                    </td>
                    <td>{fieldAgentSkillRule.createddBy}</td>
                    <td>
                      {fieldAgentSkillRule.createdTime ? (
                        <TextFormat type="date" value={fieldAgentSkillRule.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{fieldAgentSkillRule.updatedBy}</td>
                    <td>
                      {fieldAgentSkillRule.updatedTime ? (
                        <TextFormat type="date" value={fieldAgentSkillRule.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {fieldAgentSkillRule.fieldAgentSkillRuleSet ? (
                        <Link to={`/field-agent-skill-rule-set/${fieldAgentSkillRule.fieldAgentSkillRuleSet.id}`}>
                          {fieldAgentSkillRule.fieldAgentSkillRuleSet.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/field-agent-skill-rule/${fieldAgentSkillRule.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/field-agent-skill-rule/${fieldAgentSkillRule.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/field-agent-skill-rule/${fieldAgentSkillRule.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="framasaasApp.fieldAgentSkillRule.home.notFound">No Field Agent Skill Rules found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FieldAgentSkillRule;
