import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './franchise.reducer';

export const Franchise = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const franchiseList = useAppSelector(state => state.franchise.entities);
  const loading = useAppSelector(state => state.franchise.loading);
  const links = useAppSelector(state => state.franchise.links);
  const updateSuccess = useAppSelector(state => state.franchise.updateSuccess);

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
      <h2 id="franchise-heading" data-cy="FranchiseHeading">
        <Translate contentKey="framasaasApp.franchise.home.title">Franchises</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.franchise.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/franchise/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.franchise.home.createLabel">Create new Franchise</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={franchiseList ? franchiseList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {franchiseList && franchiseList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.franchise.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseName')}>
                    <Translate contentKey="framasaasApp.franchise.franchiseName">Franchise Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseName')} />
                  </th>
                  <th className="hand" onClick={sort('owner')}>
                    <Translate contentKey="framasaasApp.franchise.owner">Owner</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('owner')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="framasaasApp.franchise.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('contact')}>
                    <Translate contentKey="framasaasApp.franchise.contact">Contact</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('contact')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseStatus')}>
                    <Translate contentKey="framasaasApp.franchise.franchiseStatus">Franchise Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseStatus')} />
                  </th>
                  <th className="hand" onClick={sort('gstNumber')}>
                    <Translate contentKey="framasaasApp.franchise.gstNumber">Gst Number</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('gstNumber')} />
                  </th>
                  <th className="hand" onClick={sort('registrationNumber')}>
                    <Translate contentKey="framasaasApp.franchise.registrationNumber">Registration Number</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('registrationNumber')} />
                  </th>
                  <th className="hand" onClick={sort('performanceScore')}>
                    <Translate contentKey="framasaasApp.franchise.performanceScore">Performance Score</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('performanceScore')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.franchise.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {franchiseList.map((franchise, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/franchise/${franchise.id}`} color="link" size="sm">
                        {franchise.id}
                      </Button>
                    </td>
                    <td>{franchise.franchiseName}</td>
                    <td>{franchise.owner}</td>
                    <td>{franchise.email}</td>
                    <td>{franchise.contact}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.FranchiseStatus.${franchise.franchiseStatus}`} />
                    </td>
                    <td>{franchise.gstNumber}</td>
                    <td>{franchise.registrationNumber}</td>
                    <td>{franchise.performanceScore}</td>
                    <td>{franchise.address ? <Link to={`/address/${franchise.address.id}`}>{franchise.address.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/franchise/${franchise.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/franchise/${franchise.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/franchise/${franchise.id}/delete`)}
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
                <Translate contentKey="framasaasApp.franchise.home.notFound">No Franchises found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Franchise;
