// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract PropertySale {
    address public seller;
    address public buyer;
    string public propertyAddress;
    uint256 public propertySize;
    uint256 private _priceInWei; // Alterando para private e criando getter
    string public registerProperty;
    string public notarialDeed;
    bool private _isSold; // Alterando para private e criando getter
    uint256 public creationDate;

    event PropertyListed(address indexed seller, string propertyAddress, uint256 priceInWei, uint256 creationDate);
    event PropertyPurchased(address indexed buyer, uint256 amount);
    event SaleCompleted(address indexed seller, address indexed buyer);

    modifier onlySeller() {
        require(msg.sender == seller, "Apenas o vendedor pode chamar esta funcao");
        _;
    }

    modifier notSold() {
        require(!_isSold, "A propriedade ja foi vendida");
        _;
    }

    constructor(
        string memory _propertyAddress,
        uint256 _propertySize,
        uint256 priceInWei,
        string memory _registerProperty,
        string memory _notarialDeed
    ) {
        seller = msg.sender;
        propertyAddress = _propertyAddress;
        propertySize = _propertySize;
        _priceInWei = priceInWei;
        registerProperty = _registerProperty;
        notarialDeed = _notarialDeed;
        _isSold = false;
        creationDate = block.timestamp;

        emit PropertyListed(seller, _propertyAddress, _priceInWei, creationDate);
    }

    function buyProperty() external payable notSold {
        require(msg.value == _priceInWei, "Valor enviado nao corresponde ao preco do imovel");

        buyer = msg.sender;
        _isSold = true;

        emit PropertyPurchased(buyer, msg.value);

        payable(seller).transfer(msg.value);

        emit SaleCompleted(seller, buyer);
    }

    function getContractBalance() external view returns (uint256) {
        return address(this).balance;
    }

    function getCreationDate() external view returns (uint256) {
        return creationDate;
    }

    function getPriceInWei() external view returns (uint256) {
        return _priceInWei;
    }

    function isPropertySold() external view returns (bool) {
        return _isSold;
    }
}
