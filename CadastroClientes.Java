import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class CadastroClientes extends JFrame {

    private JTextField tfNome, tfEndereco, tfCidade;
    private JComboBox<String> cbEstado;
    private JFormattedTextField ftfTelefone;
    private JRadioButton rbAtivo, rbInativo;

    public CadastroClientes() {
        super("Cadastrar Clientes");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 360);
        setLocationRelativeTo(null); // centraliza
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        // Painel principal com padding
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Título
        JLabel lblTitulo = new JLabel("Preencha os dados corretamente e clique em Gravar Dados");
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campos (cada linha em FlowLayout para simplicidade)
        panel.add(makeRow("Nome Completo:", tfNome = new JTextField(20)));
        panel.add(makeRow("Endereço:", tfEndereco = new JTextField(20)));
        panel.add(makeRow("Cidade:", tfCidade = new JTextField(20)));

        // Estado - combo
        String[] estados = {"Selecione...", "AC","AL","AP","AM","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        cbEstado = new JComboBox<>(estados);
        cbEstado.setPreferredSize(new Dimension(120, 24));
        JPanel pEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        pEstado.add(new JLabel("Estado:"));
        pEstado.add(cbEstado);
        panel.add(pEstado);

        // Telefone - MaskFormatter
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("(##) ####-####");
            phoneFormatter.setPlaceholderCharacter('_');
            ftfTelefone = new JFormattedTextField(phoneFormatter);
            ftfTelefone.setColumns(10);
        } catch (ParseException e) {
            ftfTelefone = new JFormattedTextField();
            ftfTelefone.setColumns(10);
        }
        panel.add(makeRow("Telefone:", ftfTelefone));

        // Status - radio buttons
        rbAtivo = new JRadioButton("Ativo");
        rbInativo = new JRadioButton("Inativo");
        ButtonGroup bgStatus = new ButtonGroup();
        bgStatus.add(rbAtivo);
        bgStatus.add(rbInativo);
        JPanel pStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        pStatus.add(new JLabel("Status:"));
        pStatus.add(rbAtivo);
        pStatus.add(rbInativo);
        panel.add(pStatus);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botões
        JPanel pBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGravar = new JButton("Gravar Dados");
        JButton btnCancelar = new JButton("Cancelar Cadastro");
        pBotoes.add(btnGravar);
        pBotoes.add(btnCancelar);
        pBotoes.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(pBotoes);

        // Ações dos botões
        btnGravar.addActionListener(e -> gravar());
        btnCancelar.addActionListener(e -> limparCampos());

        add(panel);
    }

    // Helper para criar linhas com label + componente
    private JPanel makeRow(String label, JComponent comp) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(new JLabel(label));
        row.add(comp);
        return row;
    }

    // Validação simples e mensagem
    private void gravar() {
        String nome = tfNome.getText().trim();
        String endereco = tfEndereco.getText().trim();
        String cidade = tfCidade.getText().trim();
        String estado = (String) cbEstado.getSelectedItem();
        String telefone = ftfTelefone.getText().trim();
        boolean statusAtivo = rbAtivo.isSelected();
        boolean statusInativo = rbInativo.isSelected();

        // Validação
        if (nome.isEmpty()) {
            mostrarErro("Por favor, preencha o Nome Completo.");
            tfNome.requestFocus();
            return;
        }
        if (endereco.isEmpty()) {
            mostrarErro("Por favor, preencha o Endereço.");
            tfEndereco.requestFocus();
            return;
        }
        if (cidade.isEmpty()) {
            mostrarErro("Por favor, preencha a Cidade.");
            tfCidade.requestFocus();
            return;
        }
        if (estado == null || estado.equals("Selecione...")) {
            mostrarErro("Por favor, selecione o Estado.");
            cbEstado.requestFocus();
            return;
        }
        // Checar se telefone está com underscores (não preenchido)
        if (telefone.contains("_")) {
            mostrarErro("Por favor, preencha o Telefone corretamente.");
            ftfTelefone.requestFocus();
            return;
        }
        if (!statusAtivo && !statusInativo) {
            mostrarErro("Por favor, selecione o Status (Ativo/Inativo).");
            return;
        }

        // Se passou nas validações -> simular salvar (aqui só mostramos um diálogo)
        String status = statusAtivo ? "Ativo" : "Inativo";
        String mensagem = String.format("Dados gravados com sucesso!\n\nNome: %s\nEndereço: %s\nCidade: %s\nEstado: %s\nTelefone: %s\nStatus: %s",
                                         nome, endereco, cidade, estado, telefone, status);
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Se quiser limpar após gravar:
        // limparCampos();
    }

    private void limparCampos() {
        tfNome.setText("");
        tfEndereco.setText("");
        tfCidade.setText("");
        cbEstado.setSelectedIndex(0);
        ftfTelefone.setValue(null);
        rbAtivo.setSelected(false);
        rbInativo.setSelected(false);
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Executar na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new CadastroClientes());
    }
}
