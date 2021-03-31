package estrategiaconcursos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BuscaProfessorTest {
    private WebDriver navegador;
    private JavascriptExecutor js = (JavascriptExecutor) navegador;

    @Before
    public void setUp() {
        //Abrindo o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Drivers\\chromedriver.exe");
        navegador = new ChromeDriver();
        js = (JavascriptExecutor) navegador; //utilizado para rodar a rolagem da página

        //Maximar o navegador
        navegador.manage().window().maximize();

        //Acessando a página da Estratégia Consursos
        navegador.get("https://www.estrategiaconcursos.com.br/");

        //Aguardar o carregamento da página
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //PopUp da página inicial
        WebElement popupNotificacaoBotaoNao = navegador.findElement(By.id("onesignal-slidedown-cancel-button"));
        if(popupNotificacaoBotaoNao.isDisplayed()) {
            popupNotificacaoBotaoNao.click();
        }

        //Acesso do Menu POR PROFESSOR
        navegador.findElement(By.xpath("//a[@href='https://www.estrategiaconcursos.com.br/cursos/professor/']")).click();

        //Aguardar a tela carregar a listagem dos professores
        WebDriverWait waitProfessores = new WebDriverWait(navegador, 20);
        waitProfessores.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("page-result-list")));

        //Clicar no campo FLTRAR e inserir o nome Ena Loiola
        WebElement filtro = navegador.findElement(By.xpath("//input[@placeholder='Filtrar']"));
        filtro.clear();
        filtro.sendKeys("Ena Loiola");

        //PopUp que aparece novamente na segunda tela
        try {
            popupNotificacaoBotaoNao = navegador.findElement(By.id("onesignal-slidedown-cancel-button"));
            if(popupNotificacaoBotaoNao.isDisplayed()) {
                popupNotificacaoBotaoNao.click();
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }

        js.executeScript("arguments[0].parentNode.removeChild(arguments[0])", navegador.findElement(By.id("getsitecontrol")));

    }
   @After
   public void tearDown(){
        //Fechar o Navegador
        navegador.quit();
    }

    @Test
    public void testeBuscarUmProfessor() {
        String professoraBuscada = "Ena Loiola";

        //Clicar no campo FLTRAR e inserir o nome Ena Loiola
        WebElement filtro = navegador.findElement(By.xpath("//input[@placeholder='Filtrar']"));
        filtro.clear();
        filtro.sendKeys(professoraBuscada);

        WebElement resultado = navegador.findElement(By.cssSelector("div.page-result-list > section > h1 > a"));
        assertEquals(professoraBuscada, resultado.getText());
    }

    @Test
    public void testeVerificarValorTotalAVistaIgualAValorTotalParcelado() throws ParseException {
        // Rolar a barra de rolagem e selecionar a professora Ena Loiola
        WebElement linhaFiltradaDetalhes = navegador.findElement(By.cssSelector("div.page-result-list > section > a"));
        js.executeScript("arguments[0].scrollIntoView();", navegador.findElement(By.cssSelector("div.page-result-list")));
        linhaFiltradaDetalhes.click();

        //Escolha do curso "Assinatura Básica 1 Ano - Cartão até 12 x
        WebElement detalheCurso = navegador.findElement(By.cssSelector("div.js-card-prod-container > section:nth-child(1) > a"));
        js.executeScript("arguments[0].scrollIntoView();", navegador.findElement(By.cssSelector("div.cur-categories")));


        //Selecionando o primeiro curso e capturando o valor parcelado
        WebElement valorCursoPagina = navegador.findElement(By.cssSelector("div.js-card-prod-container > section:nth-child(1) > div"));
        String textoValorCurso = valorCursoPagina.getText();
        int valorPag = textoValorCurso.indexOf("$");
        String valorPag1 = textoValorCurso.substring(valorPag + 2);
        detalheCurso.click();

        //Visualizando o curso e capturando o valor total à vista na tela de detalhes
        WebElement valorCursoDetalhes = navegador.findElement(By.cssSelector(" div.cur-details-shopping-price > div"));
        System.out.println(valorCursoDetalhes.getText());
        String textoValorCursoDet = valorCursoDetalhes.getText();
        int valorDet = textoValorCursoDet.indexOf("$");
        String valorDet1 = textoValorCursoDet.substring(valorDet + 2);

        //Calculo do total a partir do valor das parcelas
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        double valorParcelaCursoPaginaListagem = nf.parse(valorPag1).doubleValue();
        double valorTotalCursoPaginaDetalhes = nf.parse(valorDet1).doubleValue();

        double totalEm12x = valorParcelaCursoPaginaListagem * 12;

        assertEquals(totalEm12x, valorTotalCursoPaginaDetalhes, 0.1);
    }

    @Test
    public void testeValorParcelamentoNaListagemIgualAValorParcelamentoDaTelaDetalhes() throws ParseException {
        // Rolar a barra de rolagem e selecionar a professora Ena Loiola
        WebElement linhaFiltradaDetalhes = navegador.findElement(By.cssSelector("div.page-result-list > section > a"));
        js.executeScript("arguments[0].scrollIntoView();", navegador.findElement(By.cssSelector("div.page-result-list")));
        linhaFiltradaDetalhes.click();

        //Escolha do curso "Assinatura Básica 1 Ano - Cartão até 12 x
        WebElement detalheCurso = navegador.findElement(By.cssSelector("div.js-card-prod-container > section:nth-child(1) > a"));
        js.executeScript("arguments[0].scrollIntoView();", navegador.findElement(By.cssSelector("div.cur-categories")));

        // Visualizando o curso e capturando o valor da parcela na tela de listagem "cursos em até 12x de R$ 89,90"
        WebElement valorCursoPagina = navegador.findElement(By.cssSelector("div.js-card-prod-container > section:nth-child(1) > div"));
        System.out.println(valorCursoPagina.getText());
        String textoValorCurso = valorCursoPagina.getText();
        int valorPag = textoValorCurso.indexOf("$");
        String valorPag1 = textoValorCurso.substring(valorPag + 2);
        System.out.println(valorPag1);
        detalheCurso.click();

        // Visualizando o curso e capturando o valor da parcela na tela de listagem "ou 12x de R$ 89,90"
        WebElement valorCursoDetalhadoParcelado = navegador.findElement(By.cssSelector("div.cur-details-shopping-installments"));
        System.out.println(valorCursoDetalhadoParcelado.getText());
        String valorCursoParc = valorCursoDetalhadoParcelado.getText();
        int valorParc = valorCursoParc.indexOf("$");
        String valorParcDt = valorCursoParc.substring(valorParc + 2);
        System.out.println(valorParcDt);

        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        double valorParcelaCursoPaginaListagem = nf.parse(valorPag1).doubleValue();
        double valorParc1 = nf.parse(valorParcDt).doubleValue();

        assertEquals(valorParcelaCursoPaginaListagem, valorParc1, 0.1);
    }

    @Test
    public void testeVerificarTotalCursosDisponiveisDoProfessorNaListagemENaPaginaDeDetalhes() {
        //Buscar a quantidade de cursos disponíveis da professora
        WebElement qtdaCursosDisponiveisEna = navegador.findElement(By.cssSelector("div.page-result-list > section > div"));
        System.out.println(qtdaCursosDisponiveisEna.getText());
        String qtdaCursosDisponiveisTelaListagem = qtdaCursosDisponiveisEna.getText();
        int qtda85 = qtdaCursosDisponiveisTelaListagem.indexOf(" ");
        String valorqtda85 = qtdaCursosDisponiveisTelaListagem.substring(0, qtda85);
        System.out.println(valorqtda85);

        //Rolar a barra de rolagem e selecionar a professora Ena Loiola
        WebElement linhaFiltradaDetalhes = navegador.findElement(By.cssSelector("div.page-result-list > section > a"));
        js.executeScript("arguments[0].scrollIntoView();", navegador.findElement(By.cssSelector("div.page-result-list")));
        linhaFiltradaDetalhes.click();

        //Buscando a quantidade de cursos disponíveis da professora na pagina de detalhes
        WebElement qtdaCursosDiponiveisNoDetalhe = navegador.findElement(By.cssSelector("div.cur-group-info > div > div > span.text"));
        System.out.println(qtdaCursosDiponiveisNoDetalhe.getText());
        String qtdaCursosDisponivelAposDetalhe = qtdaCursosDiponiveisNoDetalhe.getText();
        int qtda108 = qtdaCursosDisponivelAposDetalhe.indexOf(" ");
        String valorqtda108 = qtdaCursosDisponivelAposDetalhe.substring(0,qtda108);
        System.out.println(valorqtda108);

        //compara a quantidade de curso das duas páginas
        assertEquals(valorqtda85, valorqtda108);
    }

}
