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

import { getEntities, reset } from './service-order-franchise-assignment.reducer';

export const ServiceOrderFranchiseAssignment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderFranchiseAssignmentList = useAppSelector(state => state.serviceOrderFranchiseAssignment.entities);
  const loading = useAppSelector(state => state.serviceOrderFranchiseAssignment.loading);
  const links = useAppSelector(state => state.serviceOrderFranchiseAssignment.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderFranchiseAssignment.updateSuccess);

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
      <h2 id="service-order-franchise-assignment-heading" data-cy="ServiceOrderFranchiseAssignmentHeading">
        <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.home.title">Service Order Franchise Assignments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-franchise-assignment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.home.createLabel">
              Create new Service Order Franchise Assignment
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderFranchiseAssignmentList ? serviceOrderFranchiseAssignmentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderFranchiseAssignmentList && serviceOrderFranchiseAssignmentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('serviceOrderAssignmentStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.serviceOrderAssignmentStatus">
                      Service Order Assignment Status
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrderAssignmentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('nps')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.nps">Nps</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nps')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('assignedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.assignedTime">Assigned Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('assignedTime')} />
                  </th>
                  <th className="hand" onClick={sort('movedBackTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.movedBackTime">Moved Back Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('movedBackTime')} />
                  </th>
                  <th className="hand" onClick={sort('visitTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.visitTime">Visit Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('visitTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareOrderTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.spareOrderTime">Spare Order Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareOrderTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareDeliveryTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.spareDeliveryTime">Spare Delivery Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareDeliveryTime')} />
                  </th>
                  <th className="hand" onClick={sort('completedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.completedTime">Completed Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('completedTime')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommision')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseCommision">Franchise Commision</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommision')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseCommisionTax')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseCommisionTax">
                      Franchise Commision Tax
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseCommisionTax')} />
                  </th>
                  <th className="hand" onClick={sort('franchiseInvoicePath')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseInvoicePath">
                      Franchise Invoice Path
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('franchiseInvoicePath')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.serviceOrder">Service Order</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchise">Franchise</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderFranchiseAssignmentList.map((serviceOrderFranchiseAssignment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button
                        tag={Link}
                        to={`/service-order-franchise-assignment/${serviceOrderFranchiseAssignment.id}`}
                        color="link"
                        size="sm"
                      >
                        {serviceOrderFranchiseAssignment.id}
                      </Button>
                    </td>
                    <td>
                      <Translate
                        contentKey={`framasaasApp.ServiceOrderAssignmentStatus.${serviceOrderFranchiseAssignment.serviceOrderAssignmentStatus}`}
                      />
                    </td>
                    <td>{serviceOrderFranchiseAssignment.nps}</td>
                    <td>{serviceOrderFranchiseAssignment.isActive ? 'true' : 'false'}</td>
                    <td>
                      {serviceOrderFranchiseAssignment.assignedTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.assignedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.movedBackTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.movedBackTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.visitTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.visitTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.spareOrderTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.spareOrderTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.spareDeliveryTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.spareDeliveryTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.completedTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.completedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderFranchiseAssignment.franchiseCommision}</td>
                    <td>{serviceOrderFranchiseAssignment.franchiseCommisionTax}</td>
                    <td>{serviceOrderFranchiseAssignment.franchiseInvoicePath}</td>
                    <td>{serviceOrderFranchiseAssignment.createddBy}</td>
                    <td>
                      {serviceOrderFranchiseAssignment.createdTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderFranchiseAssignment.updatedBy}</td>
                    <td>
                      {serviceOrderFranchiseAssignment.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderFranchiseAssignment.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.serviceOrder ? (
                        <Link to={`/service-order/${serviceOrderFranchiseAssignment.serviceOrder.id}`}>
                          {serviceOrderFranchiseAssignment.serviceOrder.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {serviceOrderFranchiseAssignment.franchise ? (
                        <Link to={`/franchise/${serviceOrderFranchiseAssignment.franchise.id}`}>
                          {serviceOrderFranchiseAssignment.franchise.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-franchise-assignment/${serviceOrderFranchiseAssignment.id}`}
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
                          to={`/service-order-franchise-assignment/${serviceOrderFranchiseAssignment.id}/edit`}
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
                          onClick={() =>
                            (window.location.href = `/service-order-franchise-assignment/${serviceOrderFranchiseAssignment.id}/delete`)
                          }
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
                <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.home.notFound">
                  No Service Order Franchise Assignments found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderFranchiseAssignment;
