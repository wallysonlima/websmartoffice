const fs = require("fs");

// Caminho do arquivo compilado do contrato
const artifactPath = "artifacts/contracts/PropertySale.sol/PropertySale.json";
const abiPath = "contracts/PropertySale.abi";
const binPath = "contracts/PropertySale.bin";

// Verifica se o arquivo compilado existe
if (!fs.existsSync(artifactPath)) {
    console.error("Arquivo JSON do contrato não encontrado. Compile o contrato primeiro com `npx hardhat compile`.");
    process.exit(1);
}

// Lê o arquivo JSON gerado pelo Hardhat
const artifact = JSON.parse(fs.readFileSync(artifactPath, "utf-8"));

// Separa ABI e Bytecode
fs.writeFileSync(abiPath, JSON.stringify(artifact.abi, null, 2));
fs.writeFileSync(binPath, artifact.bytecode);

console.log("✅ ABI e Bytecode extraídos com sucesso!");
