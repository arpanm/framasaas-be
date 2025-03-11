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

import { getEntities, reset } from './franchise-allocation-rule-set.reducer';

export const FranchiseAllocationRuleSet = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const franchiseAllocationRuleSetList = useAppSelector(state => state.franchiseAllocationRuleSet.entities);
  const loading = useAppSelector(state => state.franchiseAllocationRuleSet.loading);
  const links = useAppSelector(state => state.franchiseAllocationRuleSet.links);
  const updateSuccess = useAppSelector(state => state.franchiseAllocationRuleSet.updateSuccess);

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
      <h2 id="franchise-allocation-rule-set-heading" data-cy="FranchiseAllocationRuleSetHeading">
        <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.home.title">Franchise Allocation Rule Sets</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/franchise-allocation-rule-set/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.home.createLabel">
              Create new Franchise Allocation Rule Set
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={franchiseAllocationRuleSetList ? franchiseAllocationRuleSetList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {franchiseAllocationRuleSetList && franchiseAllocationRuleSetList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('sortType')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.sortType">Sort Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('sortType')} />
                  </th>
                  <th className="hand" onClick={sort('priority')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.priority">Priority</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('priority')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {franchiseAllocationRuleSetList.map((franchiseAllocationRuleSet, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/franchise-allocation-rule-set/${franchiseAllocationRuleSet.id}`} color="link" size="sm">
                        {franchiseAllocationRuleSet.id}
                      </Button>
                    </td>
                    <td>{franchiseAllocationRuleSet.name}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.FranchiseAllocationRuleSetSortType.${franchiseAllocationRuleSet.sortType}`} />
                    </td>
                    <td>{franchiseAllocationRuleSet.priority}</td>
                    <td>{franchiseAllocationRuleSet.isActive ? 'true' : 'false'}</td>
                    <td>{franchiseAllocationRuleSet.createddBy}</td>
                    <td>
                      {franchiseAllocationRuleSet.createdTime ? (
                        <TextFormat type="date" value={franchiseAllocationRuleSet.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{franchiseAllocationRuleSet.updatedBy}</td>
                    <td>
                      {franchiseAllocationRuleSet.updatedTime ? (
                        <TextFormat type="date" value={franchiseAllocationRuleSet.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/franchise-allocation-rule-set/${franchiseAllocationRuleSet.id}`}
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
                          to={`/franchise-allocation-rule-set/${franchiseAllocationRuleSet.id}/edit`}
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
                          onClick={() => (window.location.href = `/franchise-allocation-rule-set/${franchiseAllocationRuleSet.id}/delete`)}
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
                <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.home.notFound">
                  No Franchise Allocation Rule Sets found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FranchiseAllocationRuleSet;
