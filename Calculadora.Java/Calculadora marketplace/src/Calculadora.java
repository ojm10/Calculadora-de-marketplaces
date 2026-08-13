import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculadora {

    static final Color COR_SHOPEE   = new Color(238, 77, 45);
    static final Color COR_TIKTOK   = new Color(30, 30, 30);
    static final Color COR_AMAZON   = new Color(46, 204, 113);
    static final Color COR_MERCADOLIVRE   = new Color(52, 152, 219);
    static final Color COR_VERDE    = new Color(39, 174, 96);
    static final Color COR_VERMELHO = new Color(192, 57, 43);
    static final Color COR_FUNDO    = new Color(245, 245, 245);
    static final Color COR_CARD     = Color.WHITE;
    static final Font  FONTE_TITULO = new Font("Arial", Font.BOLD, 13);
    static final Font  FONTE_LABEL  = new Font("Arial", Font.PLAIN, 12);
    static final Font  FONTE_RESULT = new Font("Arial", Font.BOLD, 22);

    public static void main(String[] args) {

        JTextField campoPreco     = new JTextField("");
        JTextField campoCusto     = new JTextField("");
        JTextField campoAfiliado  = new JTextField("");
        JCheckBox  checkCpf       = new JCheckBox("CPF (+R$ 3,00)");

        JPanel painelEntradas = new JPanel(new GridLayout(5, 2, 8, 8));
        painelEntradas.setBackground(COR_CARD);
        painelEntradas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        painelEntradas.add(rotulo("Preço de venda (R$):"));
        painelEntradas.add(estilizarCampo(campoPreco));
        painelEntradas.add(rotulo("Seu custo do produto (R$):"));
        painelEntradas.add(estilizarCampo(campoCusto));
        painelEntradas.add(rotulo("Comissão afiliado TikTok (%):"));
        painelEntradas.add(estilizarCampo(campoAfiliado));
        painelEntradas.add(checkCpf);

        JButton botaoCalcular = new JButton("CALCULAR");
        botaoCalcular.setBackground(new Color(255, 102, 0));
        botaoCalcular.setForeground(Color.WHITE);
        botaoCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCalcular.setFocusPainted(false);
        botaoCalcular.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoCalcular.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        painelEntradas.add(new JLabel());
        painelEntradas.add(botaoCalcular);

        JPanel cardShopee = criarCard(COR_SHOPEE, "🟠  SHOPEE");
        JPanel cardTikTok = criarCard(COR_TIKTOK, "⚫  TIKTOK SHOP");
        JPanel cardMercadoLivre = criarCard(COR_MERCADOLIVRE, "🔵  MERCADO LIVRE");
        JPanel cardAmazon = criarCard(COR_AMAZON, "🟢  AMAZON");

        JLabel shTaxas    = labelValor("R$ 0,00", COR_VERMELHO);
        JLabel shRepasse  = labelValor("R$ 0,00", Color.DARK_GRAY);
        JLabel shLucro    = labelValor("R$ 0,00", COR_VERDE);
        JLabel shMargem   = labelValor("0,0%",    COR_VERDE);

        JLabel ttTaxas    = labelValor("R$ 0,00", COR_VERMELHO);
        JLabel ttRepasse  = labelValor("R$ 0,00", Color.DARK_GRAY);
        JLabel ttLucro    = labelValor("R$ 0,00", COR_VERDE);
        JLabel ttMargem   = labelValor("0,0%",    COR_VERDE);

        JLabel mlTaxas    = labelValor("R$ 0,00", COR_VERMELHO);
        JLabel mlRepasse  = labelValor("R$ 0,00", Color.DARK_GRAY);
        JLabel mlLucro    = labelValor("R$ 0,00", COR_VERDE);
        JLabel mlMargem   = labelValor("0,0%",    COR_VERDE);

        JLabel amTaxas    = labelValor("R$ 0,00", COR_VERMELHO);
        JLabel amRepasse  = labelValor("R$ 0,00", Color.DARK_GRAY);
        JLabel amLucro    = labelValor("R$ 0,00", COR_VERDE);
        JLabel amMargem   = labelValor("0,0%",    COR_VERDE);

        preencherCard(cardShopee, shTaxas, shRepasse, shLucro, shMargem);
        preencherCard(cardTikTok, ttTaxas, ttRepasse, ttLucro, ttMargem);
        preencherCard(cardMercadoLivre, mlTaxas, mlRepasse, mlLucro, mlMargem);
        preencherCard(cardAmazon, amTaxas, amRepasse, amLucro, amMargem);

        JLabel labelVencedor = new JLabel("Preencha os campos e clique em Calcular", SwingConstants.CENTER);
        labelVencedor.setFont(new Font("Arial", Font.BOLD, 14));
        labelVencedor.setForeground(new Color(100, 100, 100));
        labelVencedor.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        botaoCalcular.addActionListener((ActionEvent e) -> {
            try {
                double preco    = Double.parseDouble(campoPreco.getText().replace(",", "."));
                double custo    = Double.parseDouble(campoCusto.getText().replace(",", "."));
                double pctAfi   = Double.parseDouble(campoAfiliado.getText().replace(",", ".")) / 100;
                
                double pctShopee;
                double tarifaFixaShopee;
                
                if (preco <= 79.99) {
                    pctShopee        = 0.20;
                    tarifaFixaShopee = 4.0;
                } else if (preco <= 99.99) {
                    pctShopee        = 0.14;
                    tarifaFixaShopee = 16.0;
                } else if (preco <= 199.99) {
                    pctShopee        = 0.14;
                    tarifaFixaShopee = 20.0;
                } else {
                    pctShopee        = 0.14;
                    tarifaFixaShopee = 26.0;
                }
                
                
                double taxaCpf         = checkCpf.isSelected() ? 3.0 : 0.0;
                double comissaoShopee  = preco * pctShopee;
                double totalTaxShopee  = comissaoShopee + tarifaFixaShopee + taxaCpf;
                double repasseShopee   = preco - totalTaxShopee;
                double lucroShopee     = repasseShopee - custo;
                double margemShopee    = preco > 0 ? (lucroShopee / preco) * 100 : 0;
                
                double pctTikTok;
                double tarifaFixaTikTok;
                
                if (preco < 50) {
                    pctTikTok        = 0.10;
                    tarifaFixaTikTok = 4.0;
                } else {
                    pctTikTok        = 0.06;
                    tarifaFixaTikTok = 6.0;
                }

                
                double comissaoTikTok  = preco * pctTikTok;
                double repasseAfiliado = preco * pctAfi;
                double totalTaxTikTok  = comissaoTikTok + tarifaFixaTikTok + repasseAfiliado;
                double repasseTikTok   = preco - totalTaxTikTok;
                double lucroTikTok     = repasseTikTok - custo;
                double margemTikTok    = preco > 0 ? (lucroTikTok / preco) * 100 : 0;


                double pctMercadoLivre;
                double tarifaFixaMercadoLivre;
                
                if (preco <= 79.99) {
                    pctMercadoLivre        = 0.20;
                    tarifaFixaMercadoLivre = 4.0;
                } else if (preco <= 99.99) {
                    pctMercadoLivre        = 0.14;
                    tarifaFixaMercadoLivre = 16.0;
                } else if (preco <= 199.99) {
                    pctMercadoLivre        = 0.14;
                    tarifaFixaMercadoLivre = 20.0;
                } else {
                    pctMercadoLivre        = 0.14;
                    tarifaFixaMercadoLivre = 26.0;
                }
                
                
                double comissaoMercadoLivre  = preco * pctMercadoLivre;
                double totalTaxMercadoLivre  = comissaoMercadoLivre + tarifaFixaMercadoLivre + taxaCpf;
                double repasseMercadoLivre   = preco - totalTaxMercadoLivre;
                double lucroMercadoLivre     = repasseMercadoLivre - custo;
                double margemMercadoLivre    = preco > 0 ? (lucroMercadoLivre / preco) * 100 : 0;

                double pctAmazon;
                double tarifaFixaAmazon;
                
                if (preco < 50) {
                    pctAmazon        = 0.10;
                    tarifaFixaAmazon = 4.0;
                } else {
                    pctAmazon        = 0.06;
                    tarifaFixaAmazon = 6.0;
                }

                
                double comissaoAmazon  = preco * pctAmazon;
                double totalTaxAmazon  = comissaoAmazon + tarifaFixaAmazon + repasseAfiliado;
                double repasseAmazon   = preco - totalTaxAmazon;
                double lucroAmazon     = repasseAmazon - custo;
                double margemAmazon    = preco > 0 ? (lucroAmazon / preco) * 100 : 0;


                shTaxas.setText(  String.format("R$ %.2f", totalTaxShopee));
                shRepasse.setText( String.format("R$ %.2f", repasseShopee));
                shLucro.setText(   String.format("R$ %.2f", lucroShopee));
                shMargem.setText(  String.format("%.1f%%",  margemShopee));
                shLucro.setForeground(lucroShopee >= 0 ? COR_VERDE : COR_VERMELHO);
                shMargem.setForeground(margemShopee >= 0 ? COR_VERDE : COR_VERMELHO);
                
                ttTaxas.setText(   String.format("R$ %.2f", totalTaxTikTok));
                ttRepasse.setText(  String.format("R$ %.2f", repasseTikTok));
                ttLucro.setText(    String.format("R$ %.2f", lucroTikTok));
                ttMargem.setText(   String.format("%.1f%%",  margemTikTok));
                ttLucro.setForeground(lucroTikTok >= 0 ? COR_VERDE : COR_VERMELHO);
                ttMargem.setForeground(margemTikTok >= 0 ? COR_VERDE : COR_VERMELHO);
                
                mlTaxas.setText(   String.format("R$ %.2f", totalTaxMercadoLivre));
                mlRepasse.setText(  String.format("R$ %.2f", repasseMercadoLivre));
                mlLucro.setText(    String.format("R$ %.2f", lucroMercadoLivre));
                mlMargem.setText(   String.format("%.1f%%",  margemMercadoLivre));
                mlLucro.setForeground(lucroMercadoLivre >= 0 ? COR_VERDE : COR_VERMELHO);
                mlMargem.setForeground(margemMercadoLivre >= 0 ? COR_VERDE : COR_VERMELHO);

                amTaxas.setText(   String.format("R$ %.2f", totalTaxAmazon));
                amRepasse.setText(  String.format("R$ %.2f", repasseAmazon));
                amLucro.setText(    String.format("R$ %.2f", lucroAmazon));
                amMargem.setText(   String.format("%.1f%%",  margemAmazon));
                amLucro.setForeground(lucroAmazon >= 0 ? COR_VERDE : COR_VERMELHO);
                amMargem.setForeground(margemAmazon >= 0 ? COR_VERDE : COR_VERMELHO);

                if (lucroShopee > lucroTikTok && lucroShopee > lucroAmazon && lucroShopee > lucroMercadoLivre) {
                    double diff = lucroShopee - lucroTikTok;
                    labelVencedor.setText(String.format("🏆 Melhor lucro: SHOPEE  (+R$ %.2f)", diff));
                    labelVencedor.setForeground(COR_SHOPEE);
                } else if (lucroTikTok > lucroShopee && lucroTikTok > lucroAmazon && lucroTikTok > lucroMercadoLivre) {
                    double diff = lucroTikTok - lucroShopee;
                    labelVencedor.setText(String.format("🏆 Melhor lucro: TIKTOK SHOP  (+R$ %.2f)", diff));
                    labelVencedor.setForeground(new Color(100, 100, 200));
                } else if (lucroAmazon > lucroShopee && lucroAmazon > lucroTikTok && lucroAmazon > lucroMercadoLivre) {
                    double diff = lucroAmazon - lucroShopee;
                    labelVencedor.setText(String.format("🏆 Melhor lucro: AMAZON  (+R$ %.2f)", diff));
                    labelVencedor.setForeground(new Color(100, 100, 200));
                } else if (lucroMercadoLivre > lucroShopee && lucroMercadoLivre > lucroTikTok && lucroMercadoLivre > lucroAmazon) {
                    double diff = lucroMercadoLivre - lucroShopee;
                    labelVencedor.setText(String.format("🏆 Melhor lucro: MERCADO LIVRE  (+R$ %.2f)", diff));
                    labelVencedor.setForeground(new Color(100, 100, 200));

                } else {
                    labelVencedor.setText("🤝 Lucro igual nas quatro plataformas");
                    labelVencedor.setForeground(new Color(100, 100, 100));
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "⚠️ Digite apenas números!\nUse ponto ou vírgula para decimais.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel painelCards = new JPanel(new GridLayout(2, 2, 12, 12));
        painelCards.setBackground(COR_FUNDO);
        painelCards.add(cardShopee);
        painelCards.add(cardTikTok);
        painelCards.add(cardMercadoLivre);
        painelCards.add(cardAmazon);

        JPanel painelPrincipal = new JPanel(new BorderLayout(0, 12));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        painelPrincipal.add(painelEntradas, BorderLayout.NORTH);
        painelPrincipal.add(painelCards,    BorderLayout.CENTER);
        painelPrincipal.add(labelVencedor,  BorderLayout.SOUTH);

        JFrame janela = new JFrame("Calculadora Marketplace — Shopee & TikTok Shop");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setContentPane(painelPrincipal);
        janela.pack();
        janela.setMinimumSize(new Dimension(580, 500));
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }


    static JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE_LABEL);
        return l;
    }

    static JTextField estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.BOLD, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return campo;
    }

    static JLabel labelValor(String texto, Color cor) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(FONTE_RESULT);
        l.setForeground(cor);
        return l;
    }

    static JPanel criarCard(Color corTopo, String titulo) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COR_CARD);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setBackground(corTopo);
        tituloLabel.setOpaque(true);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        card.add(tituloLabel, BorderLayout.NORTH);

        return card;
    }

    static void preencherCard(JPanel card, JLabel taxas, JLabel repasse, JLabel lucro, JLabel margem) {
        JPanel corpo = new JPanel(new GridLayout(4, 2, 6, 6));
        corpo.setBackground(COR_CARD);
        corpo.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        corpo.add(rotuloCard("Total de taxas:"));   corpo.add(taxas);
        corpo.add(rotuloCard("Repasse líquido:"));  corpo.add(repasse);
        corpo.add(rotuloCard("Lucro bruto:"));      corpo.add(lucro);
        corpo.add(rotuloCard("Margem bruta:"));     corpo.add(margem);

        card.add(corpo, BorderLayout.CENTER);
    }

    static JLabel rotuloCard(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.RIGHT);
        l.setFont(FONTE_TITULO);
        l.setForeground(new Color(100, 100, 100));
        return l;
    }
}