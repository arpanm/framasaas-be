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

import { getEntities, reset } from './franchise-user.reducer';

export const FranchiseUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const franchiseUserList = useAppSelector(state => state.franchiseUser.entities);
  const loading = useAppSelector(state => state.franchiseUser.loading);
  const links = useAppSelector(state => state.franchiseUser.links);
  const updateSuccess = useAppSelector(state => state.franchiseUser.updateSuccess);

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
      <h2 id="franchise-user-heading" data-cy="FranchiseUserHeading">
        <Translate contentKey="framasaasApp.franchiseUser.home.title">Franchise Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.franchiseUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/franchise-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.franchiseUser.home.createLabel">Create new Franchise User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={franchiseUserList ? franchiseUserList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {franchiseUserList && franchiseUserList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.franchiseUser.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('userName')}>
                    <Translate contentKey="framasaasApp.franchiseUser.userName">User Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('userName')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="framasaasApp.franchiseUser.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('contact')}>
                    <Translate contentKey="framasaasApp.franchiseUser.contact">Contact</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('contact')} />
                  </th>
                  <th className="hand" onClick={sort('userStatus')}>
                    <Translate contentKey="framasaasApp.franchiseUser.userStatus">User Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('userStatus')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.franchiseUser.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.franchiseUser.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.franchiseUser.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.franchiseUser.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.franchiseUser.franchise">Franchise</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {franchiseUserList.map((franchiseUser, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/franchise-user/${franchiseUser.id}`} color="link" size="sm">
                        {franchiseUser.id}
                      </Button>
                    </td>
                    <td>{franchiseUser.userName}</td>
                    <td>{franchiseUser.email}</td>
                    <td>{franchiseUser.contact}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.FranchiseUserStatus.${franchiseUser.userStatus}`} />
                    </td>
                    <td>{franchiseUser.createddBy}</td>
                    <td>
                      {franchiseUser.createdTime ? (
                        <TextFormat type="date" value={franchiseUser.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{franchiseUser.updatedBy}</td>
                    <td>
                      {franchiseUser.updatedTime ? (
                        <TextFormat type="date" value={franchiseUser.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {franchiseUser.franchise ? (
                        <Link to={`/franchise/${franchiseUser.franchise.id}`}>{franchiseUser.franchise.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/franchise-user/${franchiseUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/franchise-user/${franchiseUser.id}/edit`}
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
                          onClick={() => (window.location.href = `/franchise-user/${franchiseUser.id}/delete`)}
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
                <Translate contentKey="framasaasApp.franchiseUser.home.notFound">No Franchise Users found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FranchiseUser;
