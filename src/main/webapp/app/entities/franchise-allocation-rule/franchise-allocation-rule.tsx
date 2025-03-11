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

import { getEntities, reset } from './franchise-allocation-rule.reducer';

export const FranchiseAllocationRule = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const franchiseAllocationRuleList = useAppSelector(state => state.franchiseAllocationRule.entities);
  const loading = useAppSelector(state => state.franchiseAllocationRule.loading);
  const links = useAppSelector(state => state.franchiseAllocationRule.links);
  const updateSuccess = useAppSelector(state => state.franchiseAllocationRule.updateSuccess);

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
      <h2 id="franchise-allocation-rule-heading" data-cy="FranchiseAllocationRuleHeading">
        <Translate contentKey="framasaasApp.franchiseAllocationRule.home.title">Franchise Allocation Rules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.franchiseAllocationRule.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/franchise-allocation-rule/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.franchiseAllocationRule.home.createLabel">Create new Franchise Allocation Rule</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={franchiseAllocationRuleList ? franchiseAllocationRuleList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {franchiseAllocationRuleList && franchiseAllocationRuleList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('ruleType')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.ruleType">Rule Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('ruleType')} />
                  </th>
                  <th className="hand" onClick={sort('joinType')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.joinType">Join Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('joinType')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRule.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {franchiseAllocationRuleList.map((franchiseAllocationRule, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/franchise-allocation-rule/${franchiseAllocationRule.id}`} color="link" size="sm">
                        {franchiseAllocationRule.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.FranchiseAllocationRuleType.${franchiseAllocationRule.ruleType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.FranchiseAllocationRuleJoinType.${franchiseAllocationRule.joinType}`} />
                    </td>
                    <td>{franchiseAllocationRule.createddBy}</td>
                    <td>
                      {franchiseAllocationRule.createdTime ? (
                        <TextFormat type="date" value={franchiseAllocationRule.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{franchiseAllocationRule.updatedBy}</td>
                    <td>
                      {franchiseAllocationRule.updatedTime ? (
                        <TextFormat type="date" value={franchiseAllocationRule.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/franchise-allocation-rule/${franchiseAllocationRule.id}`}
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
                          to={`/franchise-allocation-rule/${franchiseAllocationRule.id}/edit`}
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
                          onClick={() => (window.location.href = `/franchise-allocation-rule/${franchiseAllocationRule.id}/delete`)}
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
                <Translate contentKey="framasaasApp.franchiseAllocationRule.home.notFound">No Franchise Allocation Rules found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FranchiseAllocationRule;
